package com.opencode.code.gui.idea.menu;


import com.opencode.code.gui.idea.frame.IdeaFrame;

import javax.swing.*;
import java.awt.*;

public class BottomConsolePanel extends JPanel {

    private final IdeaFrame ideaFrame;

    private final JTextPane p2 ;

    public BottomConsolePanel(IdeaFrame ideaFrame){
        super(new BorderLayout());
        super.setPreferredSize(new Dimension(1024,200));
        this.ideaFrame = ideaFrame;

        JPanel p1 = new JPanel(new BorderLayout());
//        Button x = new Button("X");
//        x.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//                myFrame.getMyTextAreaPanel().setPreferredSize(new Dimension(1024,600));
//            }
//        });
//        p1.add(x,BorderLayout.NORTH);


        super.add(p1,BorderLayout.NORTH);

        p2 = new JTextPane();
        p2.disable();
        p2.setDisabledTextColor(Color.BLACK);
        p2.setText("控制台~");
        p2.setBackground(Color.WHITE);

        super.add(p2,BorderLayout.CENTER);


        super.setVisible(true);

    }

    public void setText(String text){
        p2.setText(text);
    }

    public void open(){
        super.setVisible(true);
    }

}
