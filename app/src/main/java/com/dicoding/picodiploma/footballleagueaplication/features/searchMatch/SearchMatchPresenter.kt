package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch

import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.SearchMatchRepository

class SearchMatchPresenter(
    private val view: SearchMatchView,
    private val searchMatchRepository: SearchMatchRepository
) {

    fun getSearchMatchData(query: String?) {
        val listSearchMatchResult = mutableListOf<SearchMatchItem>()
        val listHomeResult = mutableListOf<String>()
        val listAwayResult = mutableListOf<String>()
        val listStadiumResult = mutableListOf<String?>()

        view.showLoading()

        searchMatchRepository.getSearchMatchData(
            query,
            object : RepositoryMultipleCallback<Set<SearchMatchItem>, List<TeamDetailResponse>> {
                override fun onDataLoaded(
                    dataMatch: Set<SearchMatchItem>,
                    dataHomeBadge: List<TeamDetailResponse>,
                    dataAwayBadge: List<TeamDetailResponse>
                ) {
                    view.hideLoading()

                    listSearchMatchResult.addAll(dataMatch)

                    dataHomeBadge.map {
                        listHomeResult.add(it.teams[0].strTeamBadge)
                        listStadiumResult.add(it.teams[0].strStadium)
                    }

                    dataAwayBadge.map {
                        listAwayResult.add(it.teams[0].strTeamBadge)
                    }

                    view.loadSearchDataToView(
                        listSearchMatchResult,
                        listHomeResult,
                        listAwayResult,
                        listStadiumResult
                    )
                }

                override fun onDataError(throwable: String) {
                    view.hideLoading()
                    view.onFailure(throwable)
                }
            })

    }
}
