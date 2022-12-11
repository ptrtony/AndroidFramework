package com.sinata.framework.coroutine

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/27
 */


interface GithubService {

    @GET("orgs/{org}/repos?per_page=100")
    fun getOrgReposCall(@Path("org") org: String): Call<List<Repo>>

    @GET("repos/{owner}/{repo}/contributors?per_page=100")
    fun getRepoContributorsCall(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<User>>
}

fun loadContributorsBlocking(service: GithubService, req: RequestData): List<User> {
    val repos = service.getOrgReposCall(req.org)
        .execute()
        .also {
            logRespo(req, it)
        }
        .body() ?: listOf()
    return repos.flatMap { repo ->
        return@flatMap service.getRepoContributorsCall(req.owner, repo.name)
            .execute()
            .also {
//                logRepo(it)
            }
            .body()!!.toList()
    }
}

fun logRespo(req: RequestData, repos: Response<List<Repo>>) {
    val reqJson = Gson().toJson(req)
    val respJson = Gson().toJson(repos)

}

fun logRepo(users: List<User>) {

}


data class RequestData(var id: String, val org: String, var owner: String)