package com.dicoding.picodiploma.footballleagueaplication.features.teams

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun loadDataToView( listBadgeTeam: MutableList<String>, listTeamName: MutableList<String>, listIdTeam: MutableList<String>)
    fun onFailure(throwable: Throwable?)
}