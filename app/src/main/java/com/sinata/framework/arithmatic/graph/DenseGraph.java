package com.sinata.framework.arithmatic.graph;

import java.util.Vector;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/12/20
 */
public class DenseGraph {
    int n , m;
    boolean directed;
    Vector<Vector<Boolean>> g;

    public DenseGraph(int n,boolean directed){
        this.n = n;
        this.directed = directed;
        this.m = 0;
        for (int i = 0; i < n; i++) {
            g = new Vector<Vector<Boolean>>();
        }
    }

    int V(){
        return n;
    }

    int E(){
        return m;
    }
}
