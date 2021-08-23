package com.opencode.dingding.td;

import com.opencode.dingding.entity.CheckLoginMessage;
import com.opencode.dingding.interfaces.LoginInterface;

public class CheckLoginAgent implements Runnable{

    private LoginInterface loginInterface;

    private String qrCodeAuth;

    private String tn;

    public CheckLoginAgent(LoginInterface loginInterface, String qrCodeAuth, String tn) {
        this.loginInterface = loginInterface;
        this.qrCodeAuth = qrCodeAuth;
        this.tn = tn;
    }

    private boolean off = true;

    @Override
    public void run() {
        while(off){

            CheckLoginMessage checkLoginMessage = loginInterface.checkLogin(this.qrCodeAuth, this.tn);
            if(checkLoginMessage.isSuccess()){
                System.out.println(checkLoginMessage);
            }
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
