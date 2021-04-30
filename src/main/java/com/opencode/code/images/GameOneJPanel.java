package com.opencode.code.images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class GameOneJPanel extends JPanel implements MouseMotionListener {

    private Integer keyCode = 20;

    private int x = 0;
    private int y = 0;

    public Integer getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(Integer keyCode) {
        this.keyCode = keyCode;
    }

    public GameOneJPanel() {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit.getScreenSize();
        this.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
        this.setBackground(Color.BLACK);

    }


    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Image image = toolkit.getImage("C:\\Users\\王添一\\Desktop\\ccc\\下载.gif");

        g2.drawImage(image,x,y,null);

        g2.setFont(new Font(null,0,50));

        g2.setColor(Color.WHITE);

        Calendar today  = Calendar.getInstance();
        today.setTime(new java.util.Date());
        Lunar lunar = new Lunar(today);

        g2.drawString(lunar.cyclical() + "年 - 农历" + lunar.toString(),100,100);
        g2.drawString(lunar.animalsYear(),100,200);
        g2.drawString("星期" + lunar.getChinaWeekdayString(today.getTime().toString().substring(0,3)),100,300);
        g2.drawString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),100,400);

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}


//
//        for(int i = 0 ; i < this.getKeyCode() ; i++){
//
//            double condition = Math.random() * 100 + 1;
//            double x = Math.random() * 2000;
//            double y = Math.random() * 2000;
//
//            if(((int)condition) % 2 == 0){
//                double data = Math.random() * 25 + 97;
//                g2.drawString((char)data + "",(int)x,(int)y);
//            }else{
//                double data = Math.random() * 25 + 65;
//                g2.drawString((char)data + "",(int)x,(int)y);
//            }
//        }
