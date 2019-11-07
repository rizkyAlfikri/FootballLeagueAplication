package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.LastMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback

class LastMatchPresenter(
    private val view: LastMatchView,
    private val lastMatchRepository: LastMatchRepository
) {

    fun getLastMatchData(idLeague: String?) {

        val listLastResult = mutableListOf<LastMatchItem>()
        val listHomeResult = mutableListOf<String>()
        val listAwayResult = mutableListOf<String>()
        val listStadiumResult = mutableListOf<String?>()
        val setDateResult = mutableSetOf<String>()

        view.showLoading()

        lastMatchRepository.getLastMatchData(
            idLeague, object : RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>> {
                override fun onDataLoaded(
                    dataMatch: Set<LastMatchItem>,
                    dataHomeBadge: List<TeamDetailResponse>,
                    dataAwayBadge: List<TeamDetailResponse>
                ) {

                    view.hideLoading()

                    dataMatch.map {
                        listLastResult.add(it)
                        setDateResult.add(it.dateEvent)
                    }

                    dataHomeBadge.map {
                        listHomeResult.add(it.teams[0].strTeamBadge)
                        listStadiumResult.add(it.teams[0].strStadium)
                    }

                    dataAwayBadge.map {
                        listAwayResult.add(it.teams[0].strTeamBadge)
                    }

                    view.loadLastMatchData(
                        listLastResult,
                        listHomeResult,
                        listAwayResult,
                        listStadiumResult,
                        setDateResult
                    )
                }

                override fun onDataError(throwable: String) {
                    view.hideLoading()
                    view.onFailure(throwable)
                }
            })
    }
}