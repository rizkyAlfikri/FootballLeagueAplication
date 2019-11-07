package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun loadToView(listTableData: List<Table>, listTeamData: List<TeamDetailItem>)
    fun onFailure(throwable: String)
}