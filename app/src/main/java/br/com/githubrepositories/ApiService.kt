package br.com.githubrepositories

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search/repositories?q=language:kotlin&sort=stars")
    fun getRepositoriesList(@Query("page") page: Int): Call<RepositoriesModel>
}