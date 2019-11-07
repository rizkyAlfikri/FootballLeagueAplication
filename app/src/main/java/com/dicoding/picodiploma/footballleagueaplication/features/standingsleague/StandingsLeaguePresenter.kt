package com.dicoding.picodiploma.footballleagueaplication.features.standingsleague

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.StandingsRepository

class StandingsLeaguePresenter(
    private val view: StandingsLeagueView,
    private val repository: StandingsRepository
) {

    fun getStandingLeague(idLeague: String?) {

        view.showLoading()

        repository.getStandingTable(idLeague, object : RepositoryCallbackTable<List<Table>, List<String>> {
            override fun onDataLoaded(dataTable: List<Table>, dataTeamBadge: List<String>) {
                view.hideLoading()
                view.loadTableDataToView(dataTable, dataTeamBadge)
            }

            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })
    }
}