package com.sinata.framework.okhttp;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/11/26
 */
class DnsDemo {


//    public static String url = "https://api.github.com/users/rengwuxian/repos";

    public static void main(String[] args) {
        run();
    }

    public static void run(){
        new Thread(() -> {
            try {
                System.out.println("Resolved address: " + InetAddress.getAllByName("hencoder.com")[0]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
