package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService.createService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class TeamDetailPresenter(private val view: TeamDetailView) {

    fun getTeamDetailData(idTeam: String) {

        val apiService = createService()
        apiService.getTeamDetailFromServer(idTeam)
            .subscribeOn(Schedulers.newThread())
            .doOnSubscribe { view.showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.hideLoading()
                    view.loadToView(it.teams)
                },
                {
                    view.hideLoading()
                    view.onFailure(it)
                }
            )
    }
}