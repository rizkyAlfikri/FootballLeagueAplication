package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamOverviewRepository

class TeamOverviewPresenter(
    private val view: TeamOverviewView,
    private val teamOverviewRepository: TeamOverviewRepository
) {

    fun getTeamOverviewData(idLeague: String?, idTeam: String?) {

        view.showLoading()

        teamOverviewRepository.getTeamOverview(
            idTeam,
            idLeague,
            object : RepositoryCallbackTable<List<Table>, List<TeamDetailItem>> {
                override fun onDataLoaded(
                    dataTable: List<Table>,
                    dataTeamBadge: List<TeamDetailItem>
                ) {
                    view.hideLoading()
                    view.loadTeamOverviewToView(dataTable, dataTeamBadge)
                }

                override fun onDataError(throwable: String) {
                    view.hideLoading()
                    view.onFailure(throwable)
                }
            })
    }
}