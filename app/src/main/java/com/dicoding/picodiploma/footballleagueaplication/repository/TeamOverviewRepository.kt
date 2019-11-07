package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamOverviewRepository {

    @SuppressLint("CheckResult")
    fun getTeamOverview(
        idTeam: String?,
        idLeague: String?,
        callback: RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>
    ) {

        val apiService = RetrofitService.createService(ApiService::class.java)

        val tableTeam =
            apiService.getTableTeam(idLeague).flatMapIterable { it.table }
                .filter { it.teamid == idTeam }.subscribeOn(Schedulers.io())

        val teamDetail = apiService.getTeamDetailFromServer(idTeam).subscribeOn(Schedulers.io())

        val listTeam = mutableListOf<TeamDetailResponse>()
        val listTable = mutableListOf<Table>()

        Observable.merge(
            tableTeam,
            teamDetail
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                callback.onDataLoaded(listTable, listTeam[0].teams)
            }
            .subscribe(
                {
                    when (it) {
                        is TeamDetailResponse -> listTeam.add(it)
                        is Table -> listTable.add(it)
                    }
                }
                ,
                {
                    callback.onDataError("Failed request team detail data to server")
                }
            )
    }
}