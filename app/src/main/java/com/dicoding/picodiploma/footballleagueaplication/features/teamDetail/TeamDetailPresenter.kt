package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamDetailRepository


class TeamDetailPresenter(
    private val view: TeamDetailView,
    private val teamDetailRepository: TeamDetailRepository
) {

    fun getTeamDetailData(idTeam: String, idLeague: String?) {

        view.showLoading()

        teamDetailRepository.getTeamDetail(idTeam, idLeague, object : RepositoryCallbackTable<List<Table>, List<TeamDetailItem>> {
            override fun onDataLoaded(
                dataTable: List<Table>,
                dataTeamBadge: List<TeamDetailItem>
            ) {
                view.hideLoading()
                view.loadToView(dataTable, dataTeamBadge)
            }

            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })
    }
}