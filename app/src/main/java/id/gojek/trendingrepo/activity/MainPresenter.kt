package id.gojek.trendingrepo.activity

import android.util.Log
import com.google.gson.Gson
import id.gojek.trendingrepo.CoroutineContextProvider
import id.gojek.trendingrepo.api.ApiRepository
import id.gojek.trendingrepo.api.TrendingRepoApi
import id.gojek.trendingrepo.model.Repo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val view: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getRepoList(sort: String, networkInfo: Boolean) {
        view.showSkeleton()
        if (networkInfo) {
            GlobalScope.launch (context.main){
                val repoData: List<Repo> = gson.fromJson(
                    apiRepository
                        .doRequest(TrendingRepoApi.getRepo()).await(),
                    Array<Repo>::class.java
                ).toList()

                val starSortData = repoData.sortedByDescending { it.stars }
                val nameSortData = repoData.sortedBy { it.name }

                try {
                    view.hideSkeleton()
                    if (sort == "star") {
                        view.showRepoList(starSortData)
                    } else if (sort == "name") {
                        view.showRepoList(nameSortData)
                    } else {
                        view.showRepoList(repoData)
                    }

                } catch (e: java.lang.RuntimeException) {
                    Log.d("gagal", "Gagal retrieve data")
                }
            }
        } else {
            view.showErrorState()
        }
    }
}