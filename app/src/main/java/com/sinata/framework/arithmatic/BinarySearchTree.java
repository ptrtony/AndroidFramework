package com.sinata.framework.arithmatic;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Title:
 * Description: 二叉搜索树   二叉树是非完全二叉树 left child  key < node key  , right child key > node key
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/1
 */


public class BinarySearchTree {

    Node root;
    Deque<Node> rootDeque = new ArrayDeque<Node>();

    public <VALUE> void insert(int key, VALUE value) {
        _insert(root, key, value);
    }

    Deque<Node> nodeDeque = new ArrayDeque();

    public Node search(int key) {
        Node node = _search(root, key);
        return node;
    }

    public boolean contain(int key) {
        boolean isContain = contain(root, key);
        return isContain;
    }


    /**
     * 前置遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 中置遍历
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 后置遍历
     */
    public void lastOrder() {
        lastOrder(root);
    }

    /**
     * 层序遍历
     */
    public void levelOrder() {
        rootDeque.push(root);
        while (!rootDeque.isEmpty()) {
            Node node = rootDeque.removeLast();
            if (node == null) return;
            System.out.println("node key:" + node.key);
            rootDeque.push(node.left);
            rootDeque.push(root.right);
        }
    }

    /**
     * 查询最小值
     */
    public int miniMin() {
        root = searchMin(root);
        return root.key;
    }


    /**
     * 查询最大值
     */
    public int miniMax() {
        Node node = searchMax(root);
        return node.key;
    }


    /**
     * 删除最小值
     */
    public Node removeMin() {
        root = removeMin(root);
        return root;
    }

    /**
     * 删除最大值
     */
    public Node removeMax() {
        root = removeMax(root);
        return root;
    }

    public Node removeMax(Node node) {
        if (node.right == null) {
            return node;
        }
        node.right = removeMax(node.right);
        return node;
    }

    public void destroy() {
        destroy(root);
    }


    public Node remove(int key) {
        root = remove(root, key);
        return root;
    }


    public Node removeDe(int key) {
        root = removeDe(root, key);
        return root;
    }


    private Node removeDe(Node node, int key) {
        if (key > node.key) {
            node.right = removeDe(node.right, key);
        } else if (key < node.key) {
            node.left = removeDe(node.left, key);
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node = null;
                return rightNode;
            }

            if (node.right == null) {
                Node leftNode = node.left;
                node = null;
                return leftNode;
            }

            Node depressor = new Node(searchMax(node.left));
            depressor.left = removeMax(node.left);
            depressor.right = node.right;
            node = null;
            return depressor;
        }
        return node;
    }


    private Node searchMin(Node node) {
        if (node.left == null) {
            return node;
        }
        return searchMin(node.left);
    }


    private Node searchMax(Node node) {
        if (node.right == null) {
            return node;
        }
        node.right = searchMax(node.right);
        return node;
    }


    private void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("node key:" + root.key);
        preOrder(root.left);
        preOrder(root.right);
    }


    private void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.println("node key:" + root.key);
        inOrder(root.right);
    }


    private void lastOrder(Node root) {
        if (root == null) return;
        lastOrder(root.left);
        lastOrder(root.right);
        System.out.println("node key:" + root.key);
    }


    private void destroy(Node root) {
        if (root == null) {
            return;
        }
        destroy(root.left);
        destroy(root.right);
        root = null;
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            return node;
        }
        node.left = removeMin(node.left);
        return node;
    }


    private Node remove(Node node, int key) {
        if (key < node.key) {
            node.left = remove(node.left, key);
            return node;
        } else if (key > node.key) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node = null;
                return rightNode;
            }

            if (node.right == null) {
                Node leftNode = node.left;
                node = null;
                return leftNode;
            }

            Node successor = new Node(searchMin(node.right));
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node = null;
            return successor;
        }

        return node;
    }

    private boolean contain(Node root, int key) {
        if (root == null) {
            return false;
        }
        if (root.key == key) return true;
        if (root.key > key) {
            contain(root.left, key);
        } else {
            contain(root.right, key);
        }

        return false;
    }

    private Node _search(Node root, int key) {
        if (root == null) {
            return null;
        }
        if (root.key == key) {
            return root;
        } else if (root.key > key) {
            _search(root.left, key);
        } else {
            _search(root.right, key);
        }
        return null;
    }


    private <VALUE> void _insert(Node root, int key, VALUE value) {
        if (root == null) {
            root = new Node(key, value);
        }
        if (root.key == key) {
            root.value = value;
        } else if (key > root.key) {
            _insert(root.right, key, value);
        } else {
            _insert(root.left, key, value);
        }
    }

    static class Node<VALUE> {
        int key;
        VALUE value;
        Node left;
        Node right;

        public Node(int key, VALUE value) {
            this.key = key;
            this.value = value;
        }

        public Node(Node node) {
            this.left = node.left;
            this.right = node.right;
            this.key = node.key;
            this.value = (VALUE) node.value;
        }
    }
}
