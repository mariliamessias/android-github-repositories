package br.com.githubrepositories

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OwnerModel (
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,
    @SerializedName("login")
    @Expose
    val name: String
)