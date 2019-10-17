package com.dicoding.picodiploma.footballleagueaplication.features.teams

import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService.createService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class TeamPresenter(private val view: TeamView) {

    fun getTeamData(idLeague: String?) {
        view.showLoading()
        val listBadge = mutableListOf<String>()
        val listTeam = mutableListOf<String>()
        val listId = mutableListOf<String>()
        val apiService = createService()

        apiService.getTeamFromServer(idLeague)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    view.hideLoading()
                    listBadge.clear()
                    listTeam.clear()
                    listId.clear()

                    it.teams.map {
                        listBadge.add(it.strTeamBadge)
                        listTeam.add(it.strTeam)
                        listId.add(it.idTeam)
                    }

                    view.loadDataToView(listBadge, listTeam, listId)

                },
                { error ->
                    view.hideLoading()
                    view.onFailure(error)
                })

    }
}