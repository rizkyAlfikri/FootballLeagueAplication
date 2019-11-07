package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.NextMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback

class NextMatchPresenter(
    private val view: NextMatchView,
    private val nextMatchRepository: NextMatchRepository
) {

    @SuppressLint("CheckResult")
    fun getNextMatchData(idLeague: String?) {

        val listNextMatchResult = mutableListOf<NextMatchItem>()
        val listHomeResult = mutableListOf<String>()
        val listAwayResult = mutableListOf<String>()
        val listStadiumResult = mutableListOf<String?>()
        val setDateResult = mutableSetOf<String>()

        view.showLoading()

        nextMatchRepository.getNextMatchData(
            idLeague,
            object : RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>> {
                override fun onDataLoaded(
                    dataMatch: Set<NextMatchItem>,
                    dataHomeBadge: List<TeamDetailResponse>,
                    dataAwayBadge: List<TeamDetailResponse>
                ) {

                    view.hideLoading()

                    dataMatch.map {
                        listNextMatchResult.add(it)
                        setDateResult.add(it.dateEvent)
                    }

                    dataHomeBadge.map {
                        listHomeResult.add(it.teams[0].strTeamBadge)
                        listStadiumResult.add(it.teams[0].strStadium)
                    }

                    dataAwayBadge.map {
                        listAwayResult.add(it.teams[0].strTeamBadge)
                    }

                    view.loadNextMatchData(
                        listNextMatchResult,
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
