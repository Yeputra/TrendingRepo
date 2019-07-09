package id.gojek.trendingrepo.activity

import android.util.Log
import com.google.gson.Gson
import id.gojek.trendingrepo.api.ApiRepository
import id.gojek.trendingrepo.api.TrendingRepoApi
import id.gojek.trendingrepo.model.Repo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(
    private val view: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getRepoList(sort: String) {
        view.showSkeleton()
        doAsync {
            val repoData: List<Repo> = gson.fromJson(
                apiRepository
                    .doRequest(TrendingRepoApi.getRepo()),
                Array<Repo>::class.java
            ).toList()

            val starSortData = repoData.sortedByDescending { it.stars }
            val nameSortData = repoData.sortedBy { it.name }

            try {
                uiThread {
                    view.hideSkeleton()
                    if (sort == "star"){
                        view.showRepoList(starSortData)
                    } else if (sort == "name"){
                        view.showRepoList(nameSortData)
                    } else {
                        view.showRepoList(repoData)
                    }
                }
            } catch (e: java.lang.RuntimeException) {
                uiThread {
                    Log.d("gagal", "Gagal retrieve data")
                }
            }
        }
    }
}