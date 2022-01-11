package com.sinata.framework.arithmatic.graph;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/12/21
 */
interface Graph {
    int V();
    AbjIterator getIterator(int v);
}
