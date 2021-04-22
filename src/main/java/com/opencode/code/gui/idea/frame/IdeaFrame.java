package com.opencode.code.gui.idea.frame;


import com.opencode.code.gui.idea.menu.BottomConsolePanel;
import com.opencode.code.gui.idea.menu.LeftMenuPanel;
import com.opencode.code.gui.idea.menu.top.TopMenuPanel;
import com.opencode.code.gui.idea.menu.top.TopMenuPanelLeft;
import com.opencode.code.gui.idea.menu.top.TopMenuPanelRight;
import com.opencode.code.gui.idea.text.IdeaTextAreaPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class IdeaFrame {

    private final JFrame jFrame;

    private final TopMenuPanelLeft topMenuPanelLeft;

    private final TopMenuPanelRight topMenuPanelRight;

    private final TopMenuPanel topMenuPanel;

    private final LeftMenuPanel leftMenuPanel;

    private final IdeaTextAreaPanel ideaTextAreaPanel;

    private BottomConsolePanel bottomConsolePanel;

    public IdeaFrame(Class<?> sourceClass) throws IOException {

        jFrame = new JFrame("IDEA 2020.8");
        jFrame.setLayout(new BorderLayout());
        jFrame.setResizable(true);
        jFrame.getContentPane().setBackground(Color.DARK_GRAY);
        jFrame.setBounds(0, 0, 1160, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setIconImage(ImageIO.read(sourceClass.getResource("timg.jfif")));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //顶部菜单
        topMenuPanel = new TopMenuPanel(this);
        //左边
        topMenuPanelLeft = new TopMenuPanelLeft(new GridLayout(1, 13),this);
        topMenuPanel.addPanel(BorderLayout.WEST,topMenuPanelLeft);

        //右边
        topMenuPanelRight = new TopMenuPanelRight(this);
        topMenuPanel.addPanel(BorderLayout.EAST,topMenuPanelRight);

        jFrame.add(topMenuPanel,BorderLayout.NORTH);

        //左侧目录
        leftMenuPanel = new LeftMenuPanel(this);
        JScrollPane jleftMenuPanel = new JScrollPane();
        jleftMenuPanel.setViewportView(leftMenuPanel);
        JScrollBar leftMenuPanelVerticalScrollBar = jleftMenuPanel.getVerticalScrollBar();
        leftMenuPanelVerticalScrollBar.setUnitIncrement(40);
        jFrame.add(jleftMenuPanel,BorderLayout.WEST);

        //文本
        JScrollPane jScrollPane = new JScrollPane();
        JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(40);
        ideaTextAreaPanel = new IdeaTextAreaPanel(new BorderLayout(),this);
        jScrollPane.setViewportView(ideaTextAreaPanel);
        jFrame.add(jScrollPane,BorderLayout.CENTER);

        jFrame.setVisible(true);

    }

    public IdeaTextAreaPanel getIdeaTextAreaPanel() {
        return ideaTextAreaPanel;
    }

    public JFrame getjFrame() {
        return jFrame;
    }

    public LeftMenuPanel getLeftMenuPanel() {
        return leftMenuPanel;
    }

    public void setBottomConsolePanel(BottomConsolePanel bottomConsolePanel) {
        this.bottomConsolePanel = bottomConsolePanel;
    }

    public BottomConsolePanel getBottomConsolePanel() {
        return bottomConsolePanel;
    }
}
