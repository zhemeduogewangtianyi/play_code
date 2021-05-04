package com.opencode.code.listeners;

import com.opencode.code.listeners.impl.ApprovalListenerEvent;
import com.opencode.code.listeners.impl.ApprovalListenerImpl;
import com.opencode.code.listeners.impl.ApprovalParam;
import com.opencode.code.listeners.interfaces.Listener;
import com.opencode.code.listeners.register.ListenerRegister;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ListenerRegister listenerRegister = new ListenerRegister();
        Listener<ApprovalListenerEvent> listener = new ApprovalListenerImpl();
        listenerRegister.addListener(listener);

        for(int i = 97 ; i < 107 ; i++){
            ApprovalParam param = new ApprovalParam();
            param.setApprover("WTY");
            param.setCreatePerson("JD-" + i);
            param.setId(Long.valueOf(i + ""));
            String name = ((char) i) + "";
            param.setName(name);
            param.setStatus(0);

            ApprovalListenerEvent event = new ApprovalListenerEvent(param);

            listenerRegister.publisherListener(event);
        }

        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        while(true){
            try{
                String next = scanner.next();
                ApprovalParam param = new ApprovalParam();
                param.setApprover("WTY");
                param.setCreatePerson("JD-" + next);
                param.setId(System.currentTimeMillis());

                param.setName(next);
                param.setStatus(0);

                ApprovalListenerEvent event = new ApprovalListenerEvent(param);

                listenerRegister.publisherListener(event);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }


    }

}
