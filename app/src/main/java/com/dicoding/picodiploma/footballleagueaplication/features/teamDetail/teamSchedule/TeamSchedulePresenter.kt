package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamScheduleRepository

class TeamSchedulePresenter(
    private val view: TeamScheduleView,
    private val repository: TeamScheduleRepository
) {

    fun getLastSchedule(idLeague: String?, idTeam: String?) {
        val listLastSchedule = mutableListOf<LastMatchItem>()
        val listHomeBadge = mutableListOf<String>()
        val listAwayBadge = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()

        view.showLoading()

        repository.getLastScheduleData(idLeague, idTeam, object : RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>> {
            override fun onDataLoaded(
                dataMatch: Set<LastMatchItem>,
                dataHomeBadge: List<TeamDetailResponse>,
                dataAwayBadge: List<TeamDetailResponse>
            ) {
                listLastSchedule.addAll(dataMatch)

                dataHomeBadge.map {
                    listHomeBadge.add(it.teams[0].strTeamBadge)
                    listStadium.add(it.teams[0].strStadium)
                }

                dataAwayBadge.map {
                    listAwayBadge.add(it.teams[0].strTeamBadge)
                }

                view.loadLastSchedule(listLastSchedule, listHomeBadge, listAwayBadge, listStadium)
            }

            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })
    }

    fun getNextSchedule(idLeague: String?, idTeam: String?) {
        val listNextSchedule = mutableListOf<NextMatchItem>()
        val listHomeBadge = mutableListOf<String>()
        val listAwayBadge = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()

        repository.getNextScheduleData(idLeague, idTeam, object : RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>> {
            override fun onDataLoaded(
                dataMatch: Set<NextMatchItem>,
                dataHomeBadge: List<TeamDetailResponse>,
                dataAwayBadge: List<TeamDetailResponse>
            ) {
                listNextSchedule.addAll(dataMatch)

                dataHomeBadge.map {
                    listHomeBadge.add(it.teams[0].strTeamBadge)
                    listStadium.add(it.teams[0].strStadium)
                }

                dataAwayBadge.map {
                    listAwayBadge.add(it.teams[0].strTeamBadge)
                }

                view.hideLoading()
                view.loadNextSchedule(listNextSchedule, listHomeBadge, listAwayBadge, listStadium)
            }

            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })
    }

}
