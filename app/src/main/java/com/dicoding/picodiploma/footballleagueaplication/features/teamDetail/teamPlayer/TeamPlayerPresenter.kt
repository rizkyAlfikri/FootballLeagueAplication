package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer

import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamPlayerRepository

class TeamPlayerPresenter(
    private val view: TeamPlayerView,
    private val playerRepository: TeamPlayerRepository
) {

    fun getPlayerFromServer(idTeam: String?) {
        val listFormation = mutableSetOf<String>()

        view.showLoading()

        playerRepository.getTeamPlayer(idTeam, object: RepositoryCallback<PlayerResponse> {
            override fun onDataLoaded(data: PlayerResponse) {
                data.player.map {
                    listFormation.add(it.strPosition)
                }

                view.loadPlayerToView(listFormation, data.player)
                view.hideLoading()
            }

            override fun onDataError(throwable: String) {
                view.onFailure(throwable)
                view.hideLoading()
            }
        })

    }
}