package id.gojek.trendingrepo.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import org.jetbrains.anko.support.v4.onRefresh

class MainActivity : AppCompatActivity(), MainView {

    private var MutableRepoCollection : MutableList<Repo> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var skeleton: Skeleton
    private lateinit var rvRepo: RecyclerView
    private lateinit var slParent: SwipeRefreshLayout
    private var sort = "default"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        presenter.getRepoList(sort)

        slParent.onRefresh {
            presenter.getRepoList(sort)
        }
    }

    private fun initView() {
        rvRepo = findViewById(R.id.rv_trending_repo)
        slParent = findViewById(R.id.sl_parent)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        adapter = MainAdapter(this, MutableRepoCollection)
        rvRepo.adapter = adapter
        rvRepo.layoutManager = LinearLayoutManager(this)

        skeleton = rvRepo.applySkeleton(R.layout.item_trending_repo, 25)
        skeleton.maskCornerRadius = 50F
        skeleton.shimmerColor = Color.parseColor("#DBDBDB")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.sort_by_stars -> {
                sort = "star"
                presenter.getRepoList(sort)
                return true
            }
            R.id.sort_by_name -> {
                sort = "name"
                presenter.getRepoList(sort)
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

    override fun showRepoList(repoCollection: List<Repo>) {
        slParent.isRefreshing = false
        MutableRepoCollection.clear()
        MutableRepoCollection.addAll(repoCollection)
        adapter.notifyDataSetChanged()
    }
}
