package com.example.hqandroidstu.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HqGitHubService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<HqGitHubRepo>>

}

