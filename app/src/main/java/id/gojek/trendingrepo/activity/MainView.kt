package id.gojek.trendingrepo.activity

import id.gojek.trendingrepo.model.Repo

interface MainView {
    fun showSkeleton()
    fun hideSkeleton()
    fun showRepoList(repoCollection: List<Repo>)
}