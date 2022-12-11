package com.sinata.framework.arithmatic.graph;

import java.util.Vector;

/**
 * Title: 稠密图 --- 邻接矩阵
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/20
 */
public class DenseGraph implements Graph{
    int n , e;
    boolean directed;
    Vector<Vector<Boolean>> g;

    public DenseGraph(int n,boolean directed){
        this.n = n;
        this.directed = directed;
        this.e = 0;
        for (int i = 0; i < n; i++) {
            g = new Vector<Vector<Boolean>>();
        }
    }

    @Override
    public int V(){
        return n;
    }

    @Override
    public AbjIterator getIterator(int v) {
        return new adjIterator(this,v);
    }

    int E(){
        return e;
    }

    public void addEagle(int v,int m){
        assert (v < n && m < n);
        if (hasEagle(v,m))return;
        Vector<Boolean> vector = new Vector<>();
        vector.add(m,true);
        g.add(v,vector);
        if (!directed){
            Vector<Boolean> vector1 = new Vector<>();
            vector1.add(v,true);
            g.add(m,vector1);
        }
        e++;
    }


    public boolean hasEagle(int v,int m){
        assert (v < n || m < n);
        return g.get(v).get(m);
    }




    public static class adjIterator implements AbjIterator {

        private DenseGraph G;
        private int v;
        private int index;

        public adjIterator(DenseGraph G, int v) {
            this.G = G;
            this.v = v;
            this.index = 0;
        }


        public int begin(){
            index = -1;
            return next();
        }


        public int next(){
            for (index += 1;index < G.V();index++) {
                if (G.g.get(v).get(index)){
                    return index;
                }
            }
            return -1;
        }

        @Override
        public boolean end(){
            return index >= G.V();
        }
    }
}
