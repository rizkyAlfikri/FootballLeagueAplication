package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch

import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.DetailMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val detailMatchRepository: DetailMatchRepository
) {

    fun getDetailMatchData(idEvent: String?) {
        view.showLoading()

        detailMatchRepository.getDetailMatch(idEvent, object: RepositoryCallback<MatchDetailResponse> {
            override fun onDataLoaded(data: MatchDetailResponse) {
                view.loadDataToView(data.events)
                view.hideLoading()
            }

            override fun onDataError(throwable: String) {
                view.onFailure(throwable)
                view.hideLoading()
            }
        })

        detailMatchRepository.getHomeTeamBadge(idEvent, object: RepositoryCallback<TeamDetailResponse> {
            override fun onDataLoaded(data: TeamDetailResponse) {
                data.teams.map {
                    view.loadHomeBadgeToView(it.strTeamBadge)
                }
                view.hideLoading()
            }

            override fun onDataError(throwable: String) {
                view.onFailure(throwable)
                view.hideLoading()
            }
        })

        detailMatchRepository.getAwayTeamBadge(idEvent, object: RepositoryCallback<TeamDetailResponse> {
            override fun onDataLoaded(data: TeamDetailResponse) {
                data.teams.map {
                    view.loadAwayBadgeToView(it.strTeamBadge)
                }
                view.hideLoading()
            }

            override fun onDataError(throwable: String) {
                view.onFailure(throwable)
                view.hideLoading()
            }
        })
    }
}