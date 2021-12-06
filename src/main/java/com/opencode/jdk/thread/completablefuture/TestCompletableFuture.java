package com.opencode.jdk.thread.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestCompletableFuture {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + 111);
        Object o = CompletableFuture.supplyAsync(new Supplier<Object>() {
            @Override
            public Object get() {
//                int i = 1/0;
                return Thread.currentThread().getName() + "11111";
            }
        }).exceptionally(new Function<Throwable, Object>() {
            @Override
            public Object apply(Throwable throwable) {
                System.out.println("exceptionally");
                return null;
            }
        }).thenAccept(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
//                System.out.println(o);
                int i = 1/0;
                System.out.println("then accept");
            }
        });

        System.out.println(Thread.currentThread().getName() + 111);
        System.out.println(Thread.currentThread().getName() + 111);
        System.out.println(Thread.currentThread().getName() + 111);
        System.out.println(Thread.currentThread().getName() + 111);

    }

}
