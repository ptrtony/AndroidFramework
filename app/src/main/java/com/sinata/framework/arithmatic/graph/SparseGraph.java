package com.sinata.framework.arithmatic.graph;

import java.util.Vector;

/**
 * Title: 稀疏图---链表
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/21
 */
public class SparseGraph implements Graph {
    private int n, e;
    private boolean directed;
    private Vector<Vector<Integer>> g;

    public SparseGraph(int n, boolean directed) {
        this.n = n;
        this.e = 0;
        this.directed = directed;
        for (int i = 0; i < n; i++) {
            g = new Vector<>();
        }
    }

    @Override
    public int V() {
        return n;
    }

    @Override
    public AbjIterator getIterator(int v) {
        return new adjIterator(this, v);
    }

    public int E() {
        return e;
    }

    public void addEagle(int v, int m) {
        assert (v < n && m < n);
        Vector<Integer> vector = new Vector<>();
        vector.add(m);
        g.add(v, vector);
        e++;
    }


    public boolean hasEagle(int v, int m) {
        assert (v > 0 && v < n && m > 0 && m < n);
        for (int i = 0; i < g.get(v).size(); i++) {
            if (g.get(v).get(i) == m) {
                return true;
            }
        }
        return false;
    }


    public static class adjIterator implements AbjIterator {
        private SparseGraph G;
        private int v;
        private int index;

        public adjIterator(SparseGraph G, int v) {
            this.G = G;
            this.v = v;
            this.index = 0;

        }

        public int begin() {
            index = 0;
            if (G.g.get(v).size() > 0) {
                return G.g.get(v).get(index);
            }
            return -1;
        }


        public int next() {
            index++;
            if (index < G.g.get(v).size()) {
                return G.g.get(v).get(index);
            }
            return -1;
        }

        public boolean end() {
            return index >= G.g.get(v).size();
        }
    }

}
