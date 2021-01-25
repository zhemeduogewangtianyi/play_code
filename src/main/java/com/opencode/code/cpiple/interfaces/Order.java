package com.opencode.code.cpiple.interfaces;

public interface Order {

    int HIGHEST_PRECEDENCE = -2147483648;
    int LOWEST_PRECEDENCE = 2147483647;

    int getOrder();

}
