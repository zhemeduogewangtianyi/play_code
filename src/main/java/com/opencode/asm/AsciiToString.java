package com.opencode.asm;

import java.util.Arrays;

public class AsciiToString {
    static String asciiToString(String val) {
        final StringBuilder sb = new StringBuilder();
        Arrays.stream((val == null ? "" : val).split(","))
                .forEach(i -> sb.append((char)Integer.parseInt(i)));
        return sb.toString();
    }

    public static void main(String[] args) {
        // Atbash result
        final String atbashResult = asciiToString("53,122,72,107,53,52,54,73,53,54,118,73,53,108,106,90");
        System.out.println(atbashResult);
    }
}