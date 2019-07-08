package id.gojek.trendingrepo.model

import com.google.gson.annotations.SerializedName

data class Repo(@SerializedName("forks")
                val forks: Int = 0,
                @SerializedName("builtBy")
                val builtBy: List<BuiltByItem>?,
                @SerializedName("author")
                val author: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("description")
                val description: String = "",
                @SerializedName("avatar")
                val avatar: String = "",
                @SerializedName("stars")
                val stars: Int = 0,
                @SerializedName("url")
                val url: String = "",
                @SerializedName("language")
                val language: String = "",
                @SerializedName("languageColor")
                val languageColor: String = "",
                @SerializedName("currentPeriodStars")
                val currentPeriodStars: Int = 0,
                @SerializedName("isExpanded")
                var isExpanded: Boolean = false)
