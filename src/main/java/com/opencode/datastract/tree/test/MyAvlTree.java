package com.opencode.datastract.tree.test;

public class MyAvlTree<K extends Comparable<K> , V> {

    private Node root;

    private int size;

    private class Node{

        K key;

        V value;

        Node left , right;

        int height;

        public Node() {
            this(null , null);
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    public MyAvlTree() {

    }

    public void put(K key , V value){
        Node newNode = new Node(key, value);
        root = put(root, newNode);
    }

    private Node put(Node node , Node newNode){
        if(node == null){
            size ++;
            return newNode;
        }

        //新的比老的小
        if(newNode.key.compareTo(node.key) < 0){
            node.left = put(node.left , newNode);
        }
        //新的比老的大
        else if(newNode.key.compareTo(node.key) > 0){
            node.right = put(node.right , newNode);
        }
        //新的和老的一样
        else{
            node.value = newNode.value;
        }

        //更新高度
        node.height = Math.max(node.height, newNode.height) + 1;

        //维护平衡 -2 -1 0 1
        int balanceFactor = getBalanceFactor(node);

        System.out.println(balanceFactor);

        // LL 左左 - 当前节点的 左 > 右 且 当前节点的 左的 左 >= 右
        if(balanceFactor > 1 && getBalanceFactor(node.left) >= 0){
            //右旋转
            return rightRotate(node);
        }

        //LR 左右 当前节点的 左 > 右 且 当前节点的 左的 左 < 右
        if(balanceFactor > 1 && getBalanceFactor(node.left) < 0){

            //先把当前节点的左子进行左旋变为 LL，再右旋
            node.left = leftRotate(node.left);

            return rightRotate(node);
        }

        //RR 右右 当前节点的 右 > 右 且 当前节点的 右的 左 <= 右
        if(balanceFactor < -1 && getBalanceFactor(node.right) <= 0 ){
            //左旋
            return leftRotate(node);
        }

        //RL 右左 当前节点的 左 < 右 且 当前节点的 右的 左 > 右
        if(balanceFactor < -1 && getBalanceFactor(node.right) > 0){
            //先把当前节点的右子树右旋转为RR，再左旋
            node.right = rightRotate(node.right);

            return leftRotate(node);
        }

        return node;

    }

    private Node leftRotate(Node y) {

        //原先右边变为根，现在右边是原先的左边，现在左边是原先的根

        Node x = y.right;
        Node t2 = x.left;

        //左旋
        x.left = y;
        y.right = t2;

        //更新height
        y.height = Math.max(getHeight(y.left) , getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left) , getHeight(x.right)) + 1;

        return x;
    }

    private Node rightRotate(Node y) {

        //原先的左边作为根，现在的左边是原先右边，现在右边是原先根

        Node x = y.left;
        Node t3 = x.right;

        //右旋
        x.right = y;
        y.left = t3;

        //更新height
        y.height = Math.max(getHeight(y.left) , getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left) , getHeight(x.right)) + 1;

        return x;
    }

    private int getBalanceFactor(Node node){
        if(node == null){
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private int getHeight(Node node){
        if(node == null){
            return 0;
        }
        return node.height;
    }

}
