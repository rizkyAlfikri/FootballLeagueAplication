package com.dicoding.picodiploma.footballleagueaplication.features.home

import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.LeagueDetailRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback


class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val leagueDetailRepo: LeagueDetailRepository
) {

    fun getLeagueDetail(idLeague: String?) {
        view.showLoading()

        leagueDetailRepo.getLeagueDetail(idLeague, object: RepositoryCallback<LeagueDetailResponse> {
            override fun onDataLoaded(data: LeagueDetailResponse) {

                var listBanner = mutableListOf<String>()
                data.leagues.map { listBanner = mutableListOf(
                    it.strBadge,
                    it.strPoster,
                    it.strFanart1,
                    it.strFanart2,
                    it.strFanart3,
                    it.strFanart4,
                    it.strTrophy) }

                view.hideLoading()
                view.loadDataToView(data)
                view.loadBannerToView(listBanner)
            }

            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })
    }
}