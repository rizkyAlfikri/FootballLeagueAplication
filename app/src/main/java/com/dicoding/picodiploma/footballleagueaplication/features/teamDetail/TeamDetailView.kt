package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun loadToView(listTeamData: List<TeamDetailItem>)
    fun onFailure(throwable: Throwable)
}