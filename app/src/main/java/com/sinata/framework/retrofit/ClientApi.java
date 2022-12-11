package com.sinata.framework.retrofit;

import com.sinata.framework.coroutine.Repo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/11/22
 */
public class ClientApi {


    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
//                .addConverterFactory()
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> repos = service.listRepos("octocat");
        try {
            //同步
            Response<List<Repo>> execute = repos.execute();

            //异步
            repos.enqueue(new Callback<List<Repo>>() {
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    Repo repo = response.body().get(0);
                    System.out.println("retrofit response:" + repo.getName());
                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
