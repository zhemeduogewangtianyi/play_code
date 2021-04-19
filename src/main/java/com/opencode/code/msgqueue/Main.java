package com.opencode.code.msgqueue;

import com.opencode.code.msgqueue.register.YellowDuckClientRegister;
import com.opencode.code.msgqueue.register.YellowDuckServerRegister;

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
            String name = split[0];
            String command = split[1];
            if("unregister".equals(command)){
                YellowDuckServerRegister.unregister(name);
                YellowDuckClientRegister.unregister(name);
            }else if("register".equals(command)){
                String[] split1 = command.split("-");
                YellowDuckClientRegister.register(name,split1[1],Integer.parseInt(split1[2]));
            }else{
                boolean send = YellowDuckClientRegister.send(name, command);
            }

        }

    }

}
