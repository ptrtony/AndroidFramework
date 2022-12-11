package com.sinata.framework.arithmatic.graph;

import java.util.Stack;
import java.util.Vector;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/22
 */
public class Path {
    int[] from;
    boolean[] visited;
    int s;
    Graph G;
    public Path(Graph graph, int s) {
        assert s > 0 && s < graph.V();
        this.G = graph;
        from = new int[graph.V()];
        visited = new boolean[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            visited[i] = false;
            from[i] = -1;
        }
        this.s = s;
        //寻路算法
        dfs(s);
    }

    private void dfs(int v) {
        visited[v] = true;
        AbjIterator iterator = G.getIterator(v);
        for (int i = iterator.begin(); !iterator.end(); i = iterator.next()) {
            if (!visited[i]){
                from[i] = v;
                dfs(i);
            }
        }
    }


    boolean hasPath(int w){
        assert w > 0 && w < G.V();
        return visited[w];
    }

    void path(int w, Vector<Integer> vec){
        Stack<Integer> s = new Stack<>();
        int p = w;
        while (p != -1){
            s.push(p);
            p = from[p];
        }
        vec.clear();
        while (!s.empty()){
            vec.add(s.pop());
        }
    }

    void showPath(int w){
        Vector<Integer> vec = new Vector<>();
        path(w,vec);
        for (int i = 0; i < vec.size(); i++) {
            System.out.println(vec.get(i));
            if (i == vec.size() - 1){
                System.out.println("end");
            }
        }
    }

}
