package id.gojek.trendingrepo

import id.gojek.trendingrepo.model.Repo

interface MainView {
    fun showSkeleton()
    fun hideSkeleton()
    fun showRepoList(repoCollection: List<Repo>)
}