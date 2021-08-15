package br.com.githubrepositories

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val BASE_URL = "https://api.github.com"
    private var apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun callRepositoriesListRequest(page: Int, callback: Callback<RepositoriesModel>) {
        apiService.getRepositoriesList(page).enqueue(callback)
    }
}