package com.opencode.code.cup;

import java.io.InputStream;

public class LookCup {

    public static void main(String[] args) {

        try {
//            final String cmd="wmic /namespace:\\\\root\\WMI path MSAcpi_ThermalZoneTemperature get CurrentTemperature";
            final String cmd="wmic /namespace:\\\\\\\\root\\\\WMI path MSAcpi_ThermalZoneTemperature get CurrentTemperature";
            Process p = Runtime.getRuntime().exec(cmd);
            p.getOutputStream().close();//这句不写就不执行
            InputStream inputStream = p.getInputStream();
            int len;
            byte[] data = new byte[1024];
            while((len = inputStream.read(data)) != -1){
                String s = new String(data, 0, len);
                System.out.println(s);
            }
//            float t=(sc.nextInt()-2732f)/10f;
//            sc.close();
//            System.out.println("当前CPU温度:"+t);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
