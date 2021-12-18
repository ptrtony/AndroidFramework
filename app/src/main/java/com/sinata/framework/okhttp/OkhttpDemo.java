package com.sinata.framework.okhttp;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/11/25
 */
public class OkhttpDemo {
    public String run(String url){
        String hostname = "publicobject.com";
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(hostname, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(@Nullable Route route, Response response) throws IOException {
               //把token更新 ....
                return response.request().newBuilder().header("Authorization","xxxxxx").build();
            }
        })
        .certificatePinner(certificatePinner);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
