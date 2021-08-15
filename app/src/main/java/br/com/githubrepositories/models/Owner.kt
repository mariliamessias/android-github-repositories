package br.com.githubrepositories.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Owner (
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,
    @SerializedName("login")
    @Expose
    val name: String
)