package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer

import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService.createService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class TeamPlayerPresenter(private val view: TeamPlayerView) {

    fun getPlayerFromServer(idTeam: String?) {
        view.showLoading()

        val listFormation = mutableSetOf<String>()
        listFormation.clear()

        val apiService = createService()
        apiService.getPlayer(idTeam).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ itResponse ->
                itResponse.player.map {
                    listFormation.add(it.strPosition)
                }

                view.hideLoading()
                view.loadPlayerToView(listFormation, itResponse.player)
            },
                {

                    view.hideLoading()
                    view.onFailure(it)

                })
    }
}