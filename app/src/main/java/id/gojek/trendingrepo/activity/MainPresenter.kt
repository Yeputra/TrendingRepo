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

    fun getRepoList() {
        view.showSkeleton()
        doAsync {
            val data: List<Repo> = gson.fromJson(
                apiRepository
                    .doRequest(TrendingRepoApi.getRepo()),
                Array<Repo>::class.java
            ).toList()

            try {
                uiThread {
                    val crashLogger = { throwable: Throwable -> throwable.printStackTrace() }
                    Log.d("retrieve", crashLogger.toString())
                    view.hideSkeleton()
                    view.showRepoList(data)
                }
            } catch (e: java.lang.RuntimeException) {
                uiThread {
                    Log.d("gagal", "Gagal retrieve data")
                }
            }
        }
    }
}