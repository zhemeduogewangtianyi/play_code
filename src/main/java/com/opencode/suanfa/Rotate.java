package com.opencode.suanfa;

public class Rotate {

    public static void main(String[] args) throws Exception {

        char[] a_arr = {'0' , '1' , '2' , '3' , '4' , '5' , '6'};

        System.out.println(a_arr);

        rotate(a_arr,2);

        System.out.println(a_arr);

    }

    private static void rotate(char[] char_arr, int distance) {
        int size = char_arr.length;
        if (size == 0)
            return;
        int mid =  -distance % size;
        if (mid < 0)
            mid += size;
        if (mid == 0)
            return;

        swap(char_arr,0, mid);
        swap(char_arr,mid, size);
        swap(char_arr,0, size);
    }

    public static void swap(char[] char_arr, int i, int j) {
        for(int left = i , mid = (i+j) >> 1, right = j - 1 ; left < mid ; left++ , right-- ){
            char_arr[left] = (char) (char_arr[left] ^ char_arr[right]);
            char_arr[right] = (char) (char_arr[left] ^ char_arr[right]);
            char_arr[left] = (char) (char_arr[left] ^ char_arr[right]);
        }

    }

}
