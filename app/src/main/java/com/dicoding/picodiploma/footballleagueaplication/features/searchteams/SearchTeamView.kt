package com.dicoding.picodiploma.footballleagueaplication.features.searchteams

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamItem

interface SearchTeamView {

    fun showLoading()
    fun hideLoading()
    fun loadDataToView(dataTable: List<Table>, dataSearchTeam: List<SearchTeamItem>)
    fun onFailure(throwable: String)
}