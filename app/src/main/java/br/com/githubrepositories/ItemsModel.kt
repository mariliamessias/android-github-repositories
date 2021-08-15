package br.com.githubrepositories

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemsModel (
   @SerializedName("stargazers_count")
   @Expose
   val startsCount: Long,
   @SerializedName("name")
   @Expose
   val name: String,
   @SerializedName("forks_count")
   @Expose
   val forksCount: Long,
   @SerializedName("owner")
   @Expose val owner: OwnerModel
)