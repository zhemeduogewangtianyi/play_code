package com.opencode.code.gui.idea.menu.top;

import com.opencode.code.gui.idea.frame.IdeaFrame;

import javax.swing.*;
import java.awt.*;

public class TopMenuPanel extends JPanel {

    private IdeaFrame ideaFrame;

    public TopMenuPanel(IdeaFrame ideaFrame) {
        super(new BorderLayout());
        this.ideaFrame = ideaFrame;
    }

    public void addPanel(String borderLayout,JPanel jPanel){
        add(jPanel,borderLayout);
    }
}
