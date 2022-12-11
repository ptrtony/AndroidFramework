package com.sinata.framework.arithmatic.graph;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/22
 */
public class Edge<T extends Number> {
    private int a, b;
    private T weight;

    public Edge(T weight, int a, int b) {
        this.weight = weight;
        this.a = a;
        this.b = b;
    }

    int v() {
        return a;
    }

    int w() {
        return b;
    }

    T wt() {
        return weight;
    }

    int other(int x){
        assert x == a || x == b;
        return x == a ? a : b;
    }

//    boolean lessThen(Edge<T> e){
//        return weight < e.weight;
//    }
}
