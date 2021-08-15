package br.com.githubrepositories

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RepositoriesModel (
    @SerializedName("items")
    @Expose
    val items: ArrayList<ItemsModel>
)

