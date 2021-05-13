package com.opencode.bug;

public class Test {

    static Integer i = 10;

    public static void main(String[] args) {

        TT t = new TT();
        t.setI(i);
        swap(t);
        System.out.println(t.getI());

    }

    public static void swap(TT i){
        i.setI(i.getI() + 100);
        i.setI(i.getI() - 1);
    }

}

class TT{
    private Integer i;

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }
}