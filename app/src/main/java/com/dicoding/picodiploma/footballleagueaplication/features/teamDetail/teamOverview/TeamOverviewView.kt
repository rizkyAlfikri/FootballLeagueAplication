package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem

interface TeamOverviewView {
    fun showLoading()
    fun hideLoading()
    fun loadTeamOverviewToView(teamTableData: List<Table>, teamDetailData: List<TeamDetailItem>)
    fun onFailure(throwable: String)
}