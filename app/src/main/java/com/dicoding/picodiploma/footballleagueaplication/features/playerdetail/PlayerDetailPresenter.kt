package com.dicoding.picodiploma.footballleagueaplication.features.playerdetail

import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.PlayerDetailRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback

class PlayerDetailPresenter(
    private val view: PlayerDetailView,
    private val playerDetailRepository: PlayerDetailRepository
) {

    fun getPlayerDetail(idPlayer: String?) {
        view.showLoading()

        playerDetailRepository.getPlayerDetail(idPlayer, object : RepositoryCallback<PlayerDetailResponse> {
            override fun onDataLoaded(data: PlayerDetailResponse) {
                view.hideLoading()
                view.loadPlayerToView(data.players)
            }

            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })

        playerDetailRepository.getPlayerHonour(idPlayer, object : RepositoryCallback<PlayerHonorsResponse> {
            override fun onDataLoaded(data: PlayerHonorsResponse) {
                view.hideLoading()
                view.loadPlayerHonorToView(data.honors)
            }

            override fun onDataError(throwable: String) {
                view.hideLoading()
                view.onFailure(throwable)
            }
        })
    }
}