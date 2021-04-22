package com.opencode.code.gui.chat.frame;

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame{

    private JPanel p1,p2,p3,p4;

    private JTextArea t1,t2;

    public ChatFrame() throws HeadlessException {
        super("Chat");
        super.setLayout(new GridLayout(2,1));
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setBounds(0, 0, 1160, 600);
        super.setLocationRelativeTo(null);


        p1 = new JPanel(new BorderLayout());

        JScrollPane jShowScrollPane = new JScrollPane();
        JScrollBar verticalScrollBar = jShowScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(40);

        t1 = new JTextArea();
        t1.requestFocus();
        t1.setForeground(Color.WHITE);
        t1.setFont(new Font("宋体", Font.BOLD, 18));
        t1.setTabSize(4);
        t1.setBackground(Color.WHITE);

        jShowScrollPane.setViewportView(t1);

        p1.add(jShowScrollPane,BorderLayout.CENTER);

        super.add(p1);

        p2 = new JPanel(new GridLayout(2,1));

        JScrollPane jScrollPane = new JScrollPane();
        JScrollBar textJScrollBar = jScrollPane.getVerticalScrollBar();
        textJScrollBar.setUnitIncrement(40);

        t2 = new JTextArea();
        t2.requestFocus();
        t2.setForeground(Color.WHITE);
        t2.setFont(new Font("宋体", Font.BOLD, 18));
        t2.setTabSize(4);
        t2.setBackground(Color.WHITE);

        p2.add(jScrollPane,BorderLayout.CENTER);

        jScrollPane.setViewportView(t2);
//
//        p3 = new JPanel(new BorderLayout());
//        p2.add(p3,BorderLayout.CENTER);

        p3 = new JPanel();
        p3.add(new Button("send"));
        p2.add(p3,BorderLayout.SOUTH);

        super.add(p2);
//        super.add(p3,1);

        super.setVisible(true);
    }
}
