package id.gojek.trendingrepo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.gson.Gson
import id.gojek.trendingrepo.api.ApiRepository
import id.gojek.trendingrepo.model.Repo

class MainActivity : AppCompatActivity(), MainView {

    private var MutableRepoCollection : MutableList<Repo> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var skeleton: Skeleton
    private lateinit var rvRepo: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvRepo = findViewById(R.id.rv_trending_repo)
        adapter = MainAdapter(MutableRepoCollection)
        rvRepo.adapter = adapter
        rvRepo.layoutManager = LinearLayoutManager(this)

        skeleton = rvRepo.applySkeleton(R.layout.item_trending_repo, 25)
        skeleton.maskCornerRadius = 50F

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        presenter.getRepoList()
    }

    override fun showSkeleton() {
        skeleton.showSkeleton()
    }

    override fun hideSkeleton() {
        skeleton.showOriginal()
    }

    override fun showRepoList(repoCollection: List<Repo>) {
        MutableRepoCollection.clear()
        MutableRepoCollection.addAll(repoCollection)
        adapter.notifyDataSetChanged()
    }
}
