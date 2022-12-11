package com.sinata.framework.arithmatic.graph;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/21
 */
public class GraphClient {

    public static void main(String[] args) {
        int N = 20;
        int M = 100;
        SparseGraph sparseGraph = new SparseGraph(N,false);
        for (int i = 0; i < M; i++) {
            int a = (int) ((Math.random() * N ) % N);
            int b = (int) ((Math.random() * N ) % N);
            sparseGraph.addEagle(a,b);
        }
        for (int i = 0; i < N; i++) {
            System.out.println("V:" + i);
            SparseGraph.adjIterator iterator = new SparseGraph.adjIterator(sparseGraph,i);
            for (int w = iterator.begin();!iterator.end();w = iterator.next()){
                System.out.print("E:" + w);
            }
        }
    }
}
