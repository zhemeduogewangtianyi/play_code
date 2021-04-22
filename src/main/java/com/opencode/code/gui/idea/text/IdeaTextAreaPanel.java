package com.opencode.code.gui.idea.text;

import com.opencode.code.gui.idea.frame.IdeaFrame;
import com.opencode.code.gui.idea.menu.BottomConsolePanel;
import com.opencode.code.gui.idea.resource.ConsoleManager;
import com.opencode.code.gui.idea.resource.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class IdeaTextAreaPanel extends JPanel {

    private final JTextArea jTextArea;

    private final BottomConsolePanel bottomConsolePanel;

    private final IdeaFrame ideaFrame;

    private String fileName;

    private String dir;

    public IdeaTextAreaPanel(BorderLayout gridLayout, IdeaFrame ideaFrame) {
        super(gridLayout);
        super.setBackground(Color.darkGray);
        this.ideaFrame = ideaFrame;

        jTextArea = new JTextArea();
        jTextArea.requestFocus();
        jTextArea.setForeground(Color.WHITE);
        jTextArea.setFont(new Font("宋体", Font.BOLD, 18));
        jTextArea.setTabSize(4);
        jTextArea.setBackground(Color.LIGHT_GRAY);

        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (e.isControlDown() && keyCode == 49) {

                    compileCode();

                } else if (e.isControlDown() && keyCode == 50) {

                    runCode();

                } else if (e.isControlDown() && keyCode == 83) {

                    saveFile();

                }
            }
        });

        this.add(jTextArea, BorderLayout.CENTER);

        bottomConsolePanel = new BottomConsolePanel(ideaFrame);

        ideaFrame.setBottomConsolePanel(bottomConsolePanel);

        this.add(bottomConsolePanel, BorderLayout.SOUTH);

    }

    public synchronized void compileCode() {
        try {
            if (fileName != null && fileName != "" && fileName.trim() != "") {

                saveFile();

                Process exec = Runtime.getRuntime().exec("cmd /c set CLASSPATH=" + dir + " && javac " + dir + fileName);
                String console = ConsoleManager.console(exec);
                bottomConsolePanel.setText(console);
            } else {
                JOptionPane.showMessageDialog(jTextArea, "无可编译文件！");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public synchronized void runCode() {

        if (fileName != null && fileName != "" && fileName.trim() != "") {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        saveFile();

                        bottomConsolePanel.open();
                        bottomConsolePanel.setText("");

                        String c2 = "cmd /c set CLASSPATH=" + dir + " && javac " + dir + fileName + " && " + "java " + fileName.substring(0, fileName.indexOf("."));
                        Process exec = Runtime.getRuntime().exec(c2);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String console = ConsoleManager.console(exec);
                                bottomConsolePanel.setText(console);
                            }
                        }).start();

                        exec.waitFor();
                        exec.destroy();
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        } else {
            JOptionPane.showMessageDialog(jTextArea, "无可运行文件！");
        }

    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setText(String text) {
        this.jTextArea.setText(text);
    }

    public void open() {
        this.setVisible(true);
    }

    public void close() {
        this.setVisible(false);
    }

    public void saveFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (fileName == null && dir == null) {
                    FileDialog save = new FileDialog(ideaFrame.getjFrame());
                    save.setVisible(true);
                    fileName = save.getFile();
                    dir = save.getDirectory();
                    ideaFrame.getLeftMenuPanel().addFirstNode(dir,fileName);
                }
                FileManager.fileSave(dir, fileName, jTextArea.getText());
            }
        }).start();
    }

}
