package com.opencode.code.msgqueue;

import com.opencode.code.msgqueue.register.YellowDuckRegister;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        YellowDuckRegister.register("WTY","127.0.0.1",8888);
        YellowDuckRegister.register("王添一","127.0.0.1",8888);
        YellowDuckRegister.register("wty","127.0.0.1",8888);
        YellowDuckRegister.register("321","127.0.0.1",8888);

        Scanner scanner = new Scanner(System.in);
        while(true){

            String next = scanner.next();
            String[] split = next.split(",");
            boolean send = YellowDuckRegister.send(split[0], split[1]);
        }

    }

}
