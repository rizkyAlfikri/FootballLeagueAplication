package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem

interface TeamOverviewView {
    fun showLoading()
    fun hideLoading()
    fun loadTeamDetailToView(teamDetailData: List<TeamDetailItem>?)
    fun loadTeamTableToView(teamTableData: List<Table>)
    fun onFailure(throwable: Throwable?)
}