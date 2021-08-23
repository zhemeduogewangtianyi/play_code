package com.opencode.dingding.pipile;

import com.opencode.dingding.interfaces.LoginInterface;
import com.opencode.dingding.interfaces.impl.LoginInterfaceImpl;
import com.opencode.dingding.td.CheckLoginAgent;

public class DingPipe {

    private LoginInterface loginInterface = new LoginInterfaceImpl();

    public void start(){
        String qrCodeAuth = loginInterface.getQrCodeAuth();
        loginInterface.openQrCode(qrCodeAuth);
        String paramToken = loginInterface.getParamToken();
        CheckLoginAgent checkLoginAgent = new CheckLoginAgent(loginInterface, qrCodeAuth, paramToken);
        new Thread(checkLoginAgent).start();
    }

}
