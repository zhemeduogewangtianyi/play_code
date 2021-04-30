package com.opencode.code.images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameOne {

    private JFrame jFrame;

    private static GameOneJPanel[] gameOneJPanels;

    public GameOne(int x, int y, GameOneJPanel gameOneJPanel) {

        jFrame = new JFrame("标题");

        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit.getScreenSize();
        jFrame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());

        jFrame.setLocation(x, y);

//        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(3);
        jFrame.getContentPane().setBackground(Color.GRAY);
        jFrame.setResizable(false);
        jFrame.setUndecorated(true);

        jFrame.add(gameOneJPanel);

        jFrame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                super.keyPressed(e);

                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    case 97:
                    case 49:
                        setSpeed(0);
                        break;
                    case 98:
                    case 50:
                        setSpeed(5);
                        break;
                    case 99:
                    case 51:
                        setSpeed(20);
                        break;
                    case 100:
                    case 52:
                        setSpeed(50);
                    case 101:
                    case 53:
                        setSpeed(100);
                        break;
                    case 102:
                    case 54:
                        setSpeed(500);
                        break;
                    default:
                        break;
                }

            }
        });

        jFrame.addMouseMotionListener(gameOneJPanel);

        jFrame.setVisible(true);

    }

    private void setSpeed(int speed) {
        for (GameOneJPanel gameOneJPanel : gameOneJPanels) {
            gameOneJPanel.setKeyCode(speed);
        }
    }

    public static void main(String[] args) {

        GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = localGraphicsEnvironment.getScreenDevices();

        gameOneJPanels = new GameOneJPanel[screenDevices.length];

        for (int i = 0; i < screenDevices.length; i++) {

            GraphicsConfiguration defaultConfiguration = screenDevices[i].getDefaultConfiguration();
            double x = defaultConfiguration.getBounds().getX();
            double y = defaultConfiguration.getBounds().getY();

            GameOneJPanel gameOneJPanel = new GameOneJPanel();
            gameOneJPanels[i] = gameOneJPanel;
            new GameOne((int) x, (int) y, gameOneJPanel);
        }


    }

}
