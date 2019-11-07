package com.dicoding.picodiploma.footballleagueaplication.features.standingsleague

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table

interface StandingsLeagueView {
    fun showLoading()
    fun hideLoading()
    fun loadTableDataToView(listTable: List<Table>, listBadge: List<String>)
    fun onFailure(throwable: String)
}