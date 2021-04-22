package com.opencode.code.gui.idea.menu.top;


import com.opencode.code.gui.idea.frame.IdeaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopMenuPanelRight extends JPanel {

    private final JButton runCode ;

    private final JButton compileCode;

    public TopMenuPanelRight(IdeaFrame ideaFrame) {

        runCode = new JButton("运行");
        runCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ideaFrame.getIdeaTextAreaPanel().runCode();
            }
        });
        this.add(runCode);

        compileCode = new JButton("编译");
        compileCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ideaFrame.getIdeaTextAreaPanel().compileCode();
            }
        });
        this.add(compileCode);

        this.setVisible(true);
    }
}
