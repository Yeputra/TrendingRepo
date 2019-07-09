package id.gojek.trendingrepo

import com.google.gson.Gson
import id.gojek.trendingrepo.activity.MainPresenter
import id.gojek.trendingrepo.activity.MainView
import id.gojek.trendingrepo.api.ApiRepository
import id.gojek.trendingrepo.api.TrendingRepoApi
import id.gojek.trendingrepo.model.Repo
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    @Mock
    private lateinit var view: MainView
    @Mock private lateinit var apiRepository: ApiRepository
    @Mock private lateinit var gson: Gson

    @Mock private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =
            MainPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getRepoList() {
        val repo: MutableList<Repo> = mutableListOf()

        val sort = "default"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TrendingRepoApi.getRepo()).await(),
                    Repo::class.java
                )
            )

            presenter.getRepoList(sort, true)

            verify(view).showSkeleton()
            verify(view).showRepoList(repo)
            verify(view).hideSkeleton()
        }

    }
}