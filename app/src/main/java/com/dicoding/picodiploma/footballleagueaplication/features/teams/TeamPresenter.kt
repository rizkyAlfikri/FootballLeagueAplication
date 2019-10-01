package com.dicoding.picodiploma.footballleagueaplication.features.teams

import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService.createService
import rx.Observer
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
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.newThread())
            .subscribe(object : Observer<TeamResponse?> {
                override fun onError(e: Throwable?) {
                    view.hideLoading()
                    view.onFailure(e)
                }

                override fun onNext(t: TeamResponse?) {
                    view.hideLoading()
                    listBadge.clear()
                    listTeam.clear()
                    listId.clear()

                    t?.teams?.map {
                        listBadge.add(it.strTeamBadge)
                        listTeam.add(it.strTeam)
                        listId.add(it.idTeam)
                    }

                    view.loadDataToView(listBadge, listTeam, listId)
                }

                override fun onCompleted() {

                }
            })


    }
}