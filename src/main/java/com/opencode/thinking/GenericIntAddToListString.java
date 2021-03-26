package com.opencode.thinking;

import java.util.List;
import java.util.ArrayList;

/**
 * 一个 int 类型的数字在不转型的前提下怎么放入 List<String>
 */
public class GenericIntAddToListString {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        List item = list;

        item.add(1);

        list = item;

        for(Object o : list){
            System.out.println(o.toString() + "   " + o.getClass().getName());
        }

    }

}
