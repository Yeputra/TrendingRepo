package id.gojek.trendingrepo.model

import com.google.gson.annotations.SerializedName

data class BuiltByItem(@SerializedName("href")
                       val href: String = "",
                       @SerializedName("avatar")
                       val avatar: String = "",
                       @SerializedName("username")
                       val username: String = "")