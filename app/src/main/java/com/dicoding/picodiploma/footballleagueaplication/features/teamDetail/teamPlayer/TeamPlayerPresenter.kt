package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer

import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.dicoding.picodiploma.footballleagueaplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.ConnectException

class TeamPlayerPresenter(
    private val view: TeamPlayerView,
    private val gson: Gson,
    private val apiRepository: ApiRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getPlayerFromServer(idTeam: String?) {
        view.showLoading()

        val listFormation = mutableSetOf<String>()
        listFormation.clear()

        GlobalScope.launch(context.main) {

            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getPlayer(idTeam)).await(),
                PlayerResponse::class.java
            )

            data.player.map {
                listFormation.add(it.strPosition)
            }

            view.hideLoading()
            try {
                view.loadPlayerToView(listFormation, data.player)
            } catch (e: ConnectException) {
                view.onFailure(e)
            } catch (e: NullPointerException) {
                view.onFailure(e)
            }
        }
    }
}