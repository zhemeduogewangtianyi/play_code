package com.opencode.code.gui.idea.menu.top;


import com.opencode.code.gui.idea.frame.IdeaFrame;
import com.opencode.code.gui.idea.resource.FileManager;
import com.opencode.code.gui.idea.text.IdeaTextAreaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopMenuPanelLeft extends JPanel {

    private final IdeaFrame jFrame;

    private static final String[][] menuNames = {
            {"File", "f"},
            {"Edit", "e"},
            {"View", "v"},
            {"Navigate", "n"},
            {"Code", "c"},
            {"Analyze", "a"},
            {"Refactor", "r"},
            {"Build", "b"},
            {"Run", "a"},
            {"Tools", "t"},
            {"VCS","c"},
            {"Window","w"},
            {"Help","h"}
    };

    public TopMenuPanelLeft(GridLayout gridLayout, IdeaFrame jFrame){
        super(gridLayout);
        this.jFrame = jFrame;

        JMenuBar jMenuBar = new JMenuBar();

        JMenu[] menus = createMenu(menuNames);
        for(int i = 0 ; i < menus.length ; i++){
            if(i == 0){
                createMenuItem(menus[i]);
            }
            jMenuBar.add(menus[i]);
        }
        this.add(jMenuBar);
    }

    private JMenu[] createMenu(String[][] arrays){
        JMenu[] jMenus = new JMenu[arrays.length];

        for(int i = 0 ; i < arrays.length ; i++){
            JMenu jMenu = new JMenu(arrays[i][0]);
//            jMenu.add(createMenuItem(i));
//            jMenu.setMnemonic((int)(arrays[i][1].charAt(0)));
            jMenus[i] = jMenu;
        }
        return jMenus;
    }

    private void createMenuItem(JMenu menu){
        JMenu aNewChild = new JMenu("New");

        JMenuItem createProject = new JMenuItem("file");
        createProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.getIdeaTextAreaPanel().setDir(null);
                jFrame.getIdeaTextAreaPanel().setFileName(null);
                jFrame.getIdeaTextAreaPanel().setText(null);
            }
        });
        aNewChild.add(createProject);
        menu.add(aNewChild);

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IdeaTextAreaPanel ideaTextAreaPanel = jFrame.getIdeaTextAreaPanel();
                FileDialog save = new FileDialog(jFrame.getjFrame());
                save.setVisible(true);

                String dir = save.getDirectory();
                String fileName = save.getFile();

                boolean b = jFrame.getLeftMenuPanel().addFirstNode(dir, fileName);
                if(b){
                    ideaTextAreaPanel.setDir(dir);
                    ideaTextAreaPanel.setFileName(fileName);
                    ideaTextAreaPanel.setText(FileManager.fileLoad(dir,fileName));
                }
            }
        });
        menu.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.getIdeaTextAreaPanel().saveFile();
            }
        });
        menu.add(save);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "确认退出？", "确定退出", JOptionPane.YES_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    System.exit(0);
                }

            }
        });
        menu.add(exit);
    }

}
