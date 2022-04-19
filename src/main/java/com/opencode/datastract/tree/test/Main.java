package com.opencode.datastract.tree.test;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        MyAvlTree<String,Object> avlTree = new MyAvlTree<>();
        for(int i = 0 ; i < 7 ; i++){
//            int random = ThreadLocalRandom.current().nextInt(1000);
            int random = i;
            avlTree.put(random + "" , i);
        }
        System.out.println(avlTree);
    }

}
