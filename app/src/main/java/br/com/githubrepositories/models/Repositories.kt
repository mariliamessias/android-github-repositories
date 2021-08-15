package br.com.githubrepositories.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Repositories (
    @SerializedName("items")
    @Expose
    val items: ArrayList<Items>
)

