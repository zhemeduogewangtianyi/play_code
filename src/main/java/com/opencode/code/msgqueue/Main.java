package com.opencode.code.msgqueue;

import com.opencode.code.msgqueue.register.YellowDuckClientRegister;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        YellowDuckClientRegister.register("127.0.0.1","127.0.0.1",8888);
        YellowDuckClientRegister.register("王添一","localhost",8888);
        YellowDuckClientRegister.register("DESKTOP-FLU626S","192.168.3.28",8888);
//        YellowDuckClientRegister.register("321","127.0.0.1",8888);

        Scanner scanner = new Scanner(System.in);
        while(true){

            String next = scanner.next();
            String[] split = next.split(",");
            boolean send = YellowDuckClientRegister.send(split[0], split[1]);
        }

    }

}
