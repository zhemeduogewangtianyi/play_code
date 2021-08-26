package com.opencode.dingding.pipile;

import com.opencode.dingding.interfaces.LoginInterface;
import com.opencode.dingding.interfaces.impl.LoginInterfaceImpl;
import com.opencode.dingding.td.CheckLoginAgent;

public class DingPipe {

    private LoginInterface loginInterface = new LoginInterfaceImpl();

    public void start(){
        String qrCodeAuth = loginInterface.getQrCodeAuth();
        loginInterface.openQrCode(qrCodeAuth);
        CheckLoginAgent checkLoginAgent = new CheckLoginAgent(loginInterface, qrCodeAuth);
        checkLoginAgent.checkLogin(new Thread(checkLoginAgent));
    }

}
