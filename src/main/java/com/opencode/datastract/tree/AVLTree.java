package com.opencode.datastract.tree;

import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V> {

    private Node root;

    private int size;

    private class Node {
        K key;

        V value;

        Node left, right;

        int height;

        Node() {
            this(null, null);
        }

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    public AVLTree() {
    }

    /*** 获取节点的高度 */
    private int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /*** 获取节点的平衡因子 */
    private int getBalanceFactor(Node node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    /*** 判断二叉树是否为二分搜索树 */
    public boolean isBinarySearchTree() {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i).compareTo(keys.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    /*** 是否为平衡二叉树 */
    public boolean isBalanceTree() {
        return isBalanceTree(root);
    }

    private boolean isBalanceTree(Node node) {
        if (node == null) {
            return true;
        }

        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalanceTree(node.left) && isBalanceTree(node.right);
    }

    /*** 中序遍历 */
    private void inOrder(Node node, ArrayList<K> keys) {
        if (node == null)
            return;

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    public void put(K key, V value) {
        Node newNode = new Node(key, value);
        root = put(root, newNode);
    }

    /*** 插入一个元素，返回插入新节点后树的根 */
    private Node put(Node node, Node newNode) {
        if (node == null) {
            size++;
            return newNode;
        }

        if (newNode.key.compareTo(node.key) < 0) {
            node.left = put(node.left, newNode);
        } else if (newNode.key.compareTo(node.key) > 0) {
            node.right = put(node.right, newNode);
        } else {
            node.value = newNode.value;
        }

        // 更新高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        // 维护平衡
        int balanceFactor = getBalanceFactor(node);

        // LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            // 右旋转
            return rightRotate(node);
        }

        // LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            // 先对当前节点的左孩子进行左旋转转变为LL，然后在进行右旋转
            node.left = leftRotate(node.left);
            // 右旋转
            return rightRotate(node);
        }

        // RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            // 左旋转
            return leftRotate(node);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.left) > 0) {
            // 先对当前节点的右孩子进行右旋转转变为RR，然后在进行左旋转
            node.right = rightRotate(node.right);
            // 左旋转
            return leftRotate(node);
        }

        return node;
    }

    /**
     * RR
     * <p>
     * / 对节点进行左旋转操作，返回左旋转之后新的根节点
     * /        y                            x
     * /       / \                         /   \
     * /      T1  x     向右旋转(y)       y     z
     * /         / \    -------------->  / \   / \
     * /        T2  z                   T1 T2 T3 T4
     * /           / \
     * /          T3 T4
     */
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node t2 = x.left;

        // 右旋
        x.left = y;
        y.right = t2;

        //更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * LL
     * <p>
     * / 对节点进行右旋转操作，返回右旋转之后新的根节点
     * /        y                            x
     * /       / \                         /   \
     * /      x   T4     向右旋转(y)      z     y
     * /     / \       -------------->  / \   /  \
     * /    z  T3                      T1 T2 T3  T4
     * /   / \
     * /  T1 T2
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t3 = x.right;

        // 右旋
        x.right = y;
        y.left = t3;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    private Node getNode(Node node, K key) {
        Node current = node;
        while (current != null) {
            if (current.key.compareTo(key) < 0) {
                current = current.left;
            } else if (current.key.compareTo(key) > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    public V remove(K key) {
        Node node = getNode(root, key);
        if (node == null) {
            return null;
        }
        root = remove(root, key);
        return node.value;
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }

        if (node.key.compareTo(key) > 0) {
            node.left = remove(node.left, key);

        }
        if (node.key.compareTo(key) < 0) {
            node.right = remove(node.right, key);
        }

        // 当前node即为目标node，删除当前node即可
        // 右子树为空
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        // 左子树为空
        if (node.left == null) {
            return removeRight(node);
        }

        // 左右子树都不为空，找到前驱或者后继进行替换
        Node leftNode = node.left;
        Node rightNode = node.right;
        node.left = null;
        node.right = null;

        Node successor = rightNode;
        while (successor.left != null) {
            successor = successor.left;
        }

        rightNode = removeMin(rightNode);

        successor.left = leftNode;
        successor.right = rightNode;
        return successor;
    }

    private Node removeRight(Node node) {
        Node rightNode = node.right;
        node.right = null;
        size--;
        return rightNode;
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public static void main(String[] args) {
        AVLTree<String,Object> avlTree = new AVLTree<>();
        for(int i = 7 ; i > 0 ; i--){
//            int random = ThreadLocalRandom.current().nextInt(1000);
            int random = i;
            avlTree.put(random + "" , i);
        }
        System.out.println(avlTree.toString());
    }
}
