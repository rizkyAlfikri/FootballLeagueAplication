package com.dicoding.picodiploma.footballleagueaplication.features.searchteams

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamItem
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.SearchTeamRepository

class SearchTeamPresenter(
    private val view: SearchTeamView,
    private val repository: SearchTeamRepository
) {

    fun getSearchTeam(query: String?, idLeague: String?) {

        val listSearchTeam = mutableListOf<SearchTeamItem>()
        val listTableTeam = mutableListOf<Table>()

        view.showLoading()

        repository.getSearchTeamData(query, idLeague, object : RepositoryCallbackTable<Set<Table>, Set<SearchTeamItem>> {
            override fun onDataLoaded(dataTable: Set<Table>, dataTeamBadge: Set<SearchTeamItem>) {
                listSearchTeam.addAll(dataTeamBadge)
                listTableTeam.addAll(dataTable)
                view.hideLoading()
                view.loadDataToView(listTableTeam, listSearchTeam)
            }


            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })
    }
}