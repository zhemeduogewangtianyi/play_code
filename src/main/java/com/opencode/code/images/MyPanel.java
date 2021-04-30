package com.opencode.code.images;

import java.awt.*;
import javax.swing.*;

public class MyPanel extends JPanel
{
    private int r = 150;
    private int angle = 0;

    public void paintComponent(Graphics g)
    {
        int x = (int)(r/2*Math.cos(angle/180.0*Math.PI));
        int y = -(int)(r/2*Math.sin(angle/180.0*Math.PI));
        g.translate(r+10, r+10);
        g.setColor(Color.BLACK);
        g.fillArc(-r, -r, 2*r, 2*r, angle, -180);
        g.setColor(Color.WHITE);
        g.fillArc(-r, -r, 2*r, 2*r, angle, 180);
        g.fillArc(x-r/2, y-r/2, r, r, angle, -180);
        g.setColor(Color.BLACK);
        g.fillArc(-x-r/2, -y-r/2, r, r, angle, 180);
        g.drawOval(-r, -r, 2*r, 2*r);
        g.fillOval(x-10, y-10, 20, 20);
        g.setColor(Color.WHITE);
        g.fillOval(-x-10, -y-10, 20, 20);
    }

    public void setAngle(int angle)
    {
        while(true)
        {
            if(angle < 0)
                angle = angle + 360;
            if(angle >= 360)
                angle = angle - 360;
            else break;
        }
        this.angle = angle;
    }

    public int getAngle()
    {
        return angle;
    }

    public static void main(String[] Args)
    {
        JFrame f = new JFrame();
        final MyPanel aPanel = new MyPanel();
        f.add(aPanel);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 350);
        f.setLocation(300, 200);
        Thread aThread = new Thread(){
            public void run()
            {
                while(true)
                {
                    try
                    {
                        aPanel.setAngle(aPanel.getAngle() + 1);//加一逆时针转，减一顺时针转
                        aPanel.repaint();
                        this.sleep(10);//此处可以调节转动速度
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        aThread.start();
    }
}