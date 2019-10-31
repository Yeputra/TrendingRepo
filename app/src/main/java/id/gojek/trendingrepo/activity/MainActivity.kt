package id.gojek.trendingrepo.activity

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.gson.Gson
import id.gojek.trendingrepo.R
import id.gojek.trendingrepo.api.ApiRepository
import id.gojek.trendingrepo.model.Repo
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh

class MainActivity : AppCompatActivity(), MainView {

    private var mutableRepoCollection: MutableList<Repo> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var skeleton: Skeleton
    private lateinit var rvRepo: RecyclerView
    private lateinit var slParent: SwipeRefreshLayout
    private lateinit var llOfflineState: LinearLayout
    private lateinit var btnRetry: Button
    private var sort = "default"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        presenter.getRepoList(sort, isNetworkConnected())

        if (savedInstanceState != null) {
            val savedRepoList: ArrayList<Repo>
            savedRepoList = savedInstanceState.getParcelableArrayList("repo_list")
            adapter = MainAdapter(this, savedRepoList)
            rvRepo.setAdapter(adapter)
        } else {
            presenter.getRepoList(sort, isNetworkConnected())
        }

        slParent.onRefresh {
            presenter.getRepoList(sort, isNetworkConnected())
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList("repo_list", ArrayList<Parcelable>(mutableRepoCollection))
    }

    private fun initView() {
        rvRepo = findViewById(R.id.rv_trending_repo)
        slParent = findViewById(R.id.sl_parent)
        llOfflineState = findViewById(R.id.ll_offline_state)
        btnRetry = findViewById(R.id.btn_retry)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        adapter = MainAdapter(this, mutableRepoCollection)
        rvRepo.adapter = adapter
        rvRepo.layoutManager = LinearLayoutManager(this)

        skeleton = rvRepo.applySkeleton(R.layout.item_trending_repo, 25)
        skeleton.maskCornerRadius = 50F
        skeleton.shimmerColor = Color.parseColor("#DBDBDB")

        btnRetry.onClick {
            rvRepo.visibility = View.VISIBLE
            llOfflineState.visibility = View.GONE
            btnRetry.visibility = View.GONE
            presenter.getRepoList(sort, isNetworkConnected())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.sort_by_stars -> {
                sort = "star"
                presenter.getRepoList(sort, isNetworkConnected())
                return true
            }
            R.id.sort_by_name -> {
                sort = "name"
                presenter.getRepoList(sort, isNetworkConnected())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showSkeleton() {
        skeleton.showSkeleton()
    }

    override fun hideSkeleton() {
        skeleton.showOriginal()
    }

    override fun showErrorState() {
        slParent.isRefreshing = false
        llOfflineState.visibility = View.VISIBLE
        btnRetry.visibility = View.VISIBLE
        rvRepo.visibility = View.GONE
    }

    override fun showRepoList(repoCollection: List<Repo>) {
        slParent.isRefreshing = false
        rvRepo.visibility = View.VISIBLE
        llOfflineState.visibility = View.GONE
        btnRetry.visibility = View.GONE
        mutableRepoCollection.clear()
        mutableRepoCollection.addAll(repoCollection)
        adapter.notifyDataSetChanged()
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
