package com.opencode.vector;

import java.util.ArrayList;
import java.util.List;

public class PrintUnicode {

    public static void main(String[] args) {

        List list = new ArrayList<>();

        for (char c = '\u4e00'; c < '\u9fa5'; c++) {
            System.out.println(c);
            list.add(c);
        }

        System.out.println(list);

    }

}
