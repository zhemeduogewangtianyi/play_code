package com.opencode.thinking;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class TryCatchFinallyReturn {

    public static void main(String[] args) {

        int test = test();
        System.out.println(test);

//        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(Object.class.getName());
//        System.out.println(fullyQualifiedJavaType);

    }

    public static int test(){

        int ata = 2019;
        try{
            return ata++;
        }finally {
            switch (ata) {
                case 2019 :
                    System.out.println("hi " + (ata++));
                case 2020 :
                    System.out.println("happy ");
                case 2021 :
                    System.out.println("new year " + (++ata));
            }
        }
    }

}
