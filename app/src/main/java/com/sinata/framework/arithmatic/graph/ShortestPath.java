package com.sinata.framework.arithmatic.graph;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

/**
 * Title:
 * Description: 最短路径 - 广度优先最短路径遍历
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/12/22
 */
public class ShortestPath {
    int[] from;
    boolean[] visited;
    int s;
    Graph G;
    private int[] ord;

    public ShortestPath(Graph graph, int s) {
        assert s > 0 && s < graph.V();
        this.G = graph;
        from = new int[graph.V()];
        visited = new boolean[graph.V()];
        ord = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            visited[i] = false;
            from[i] = -1;
        }
        this.s = s;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        //无向图最短路径算法
        while (!queue.isEmpty()) {
            int v = queue.remove();
            AbjIterator iterator = G.getIterator(v);
            for (int i = iterator.begin(); !iterator.end(); i = iterator.next()) {
                if (!visited[i]) {
                    queue.add(i);
                    from[i] = v;
                    ord[i] = ord[v] + 1;
                }
            }
        }

    }
}
