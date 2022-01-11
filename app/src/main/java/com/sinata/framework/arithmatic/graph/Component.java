package com.sinata.framework.arithmatic.graph;

/**
 * Title:
 * Description: 深度优先遍历
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/12/21
 */
public class Component {
    private Graph G;
    boolean[] visited;
    int ccount;
    int[] id;
    public Component(Graph graph){
        visited = new boolean[G.V()];
        ccount = 0;
        for (int i = 0; i < G.V(); i++) {
            visited[i] = false;
        }

        for (int i = 0; i <G.V(); i++) {
            if (!visited[i]){
                dfs(i);
                ccount ++;
            }
        }
        id = new int[G.V()];
    }


    public int count(){
        return ccount;
    }

    private void dfs(int v) {
        visited[v] = true;
        id[v] = ccount;
        AbjIterator abjIterator = G.getIterator(v);
        for (int w = abjIterator.begin() ; !abjIterator.end() ; w = abjIterator.next()) {
            if (!visited[w]){
                dfs(w);
            }
        }
    }

    public boolean isConnected(int v,int w){
        assert (v > 0 && v < G.V() && w > 0 && w < G.V());
        return id[v] == id[w];
    }
}
