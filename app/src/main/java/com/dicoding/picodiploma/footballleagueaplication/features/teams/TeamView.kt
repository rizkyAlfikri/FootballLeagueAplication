package com.dicoding.picodiploma.footballleagueaplication.features.teams

import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun loadDataToView(teamResponse: TeamResponse)
    fun onFailure(throwable: String)
}