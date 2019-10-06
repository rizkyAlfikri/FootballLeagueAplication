package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService.createService
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class TeamOverviewPresenter(private val view: TeamOverviewView) {

    fun getTeamOverviewData(idLeague: String?, idSeason: String, idTeam: String?) {
        view.showLoading()

        val listTable = mutableListOf<Table>()
        val apiService = createService()
        apiService.getTeamDetailFromServer(idTeam)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TeamDetailResponse?> {
                override fun onError(e: Throwable?) {
                    view.hideLoading()
                    view.onFailure(e)
                }

                override fun onNext(t: TeamDetailResponse?) {

                    view.hideLoading()
                    view.loadTeamDetailToView(t?.teams)

                }

                override fun onCompleted() {

                }
            })

        apiService.getTableTeam(idLeague, idSeason)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TeamTableResponse?> {
                override fun onError(e: Throwable?) {
                    view.hideLoading()
                    view.onFailure(e)
                }

                override fun onNext(t: TeamTableResponse?) {
                    t?.table?.map {
                        if (it.teamid == idTeam) {
                            listTable.add(it)
                        }
                    }

                    view.hideLoading()
                    view.loadTeamTableToView(listTable)
                }

                override fun onCompleted() {

                }
            })
    }
}