package id.gojek.trendingrepo.api

import android.net.Uri
import id.gojek.trendingrepo.BuildConfig

object TrendingRepoApi {

    fun getRepo(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("repositories")
            .build()
            .toString()
    }

}