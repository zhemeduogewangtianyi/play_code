package com.opencode.jdk.thread.use.waitAndNotify02;

public class AlternateNode {

    public static void main(String[] args) {
        AlternateNode node = new AlternateNode();
        node.add(new Thread("1"));
        node.add(new Thread("2"));
        node.add(new Thread("3"));
        node.del(node.next().getT());
        node.del(node.next().getT());
        node.del(node.next().getT());
        System.out.println(node);
    }

    private Thread t;

    private AlternateNode next;

    private AlternateNode pre;

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public AlternateNode next(){
        AlternateNode temp = null;
        AlternateNode node = this;
        while((temp = node.next) != null){
            node = temp;
        }
        return node;
    }

    public void add(Thread t){
        if(t != null && this.t == null){
            this.t = t;
            return;
        }else{
            AlternateNode node = this;
            while(node.next != null){
                node = node.next;
            }
            AlternateNode nextNode = new AlternateNode();
            nextNode.setT(t);
            node.next = nextNode;
            nextNode.pre = node;
        }
    }

    public void del(Thread t){
        if(t == null){
            return;
        }else{
            AlternateNode node = this;
            while(node != null && node.getT() != t){
                node = node.next;
            }
            if(node == null){
                this.t = null;
            }else{
                if(node.pre != null){
                    node.pre.next = null;
                }else{
                    this.t = null;
                }
                if(this.next != null){
                    this.next = node.pre;
                }
            }
        }
    }

}
