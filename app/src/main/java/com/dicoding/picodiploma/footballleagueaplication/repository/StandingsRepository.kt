package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StandingsRepository {

    @SuppressLint("CheckResult")
    fun getStandingTable(
        idLeague: String?,
        callbackVar: RepositoryCallbackTable<List<Table>, List<String>>
    ) {
        val apiService = RetrofitService.createService(ApiService::class.java)
        val listTable = mutableListOf<Table>()
        val listBadge = mutableListOf<String>()

        apiService.getTableTeam(idLeague).subscribeOn(Schedulers.io())
            .flatMapIterable { it.table }
            .flatMap({
                apiService.getTeamDetailFromServer(it.teamid)
            },
                { t1: Table, t2: TeamDetailResponse ->
                    Pair(t1, t2)
                })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                callbackVar.onDataLoaded(listTable, listBadge)
            }
            .subscribe({ result ->
                if (result != null) {
                    listBadge.add(result.second.teams[0].strTeamBadge)
                    listTable.add(result.first)
                } else {
                    callbackVar.onDataError("Failed, table data is null")
                }

            },
                {
                    callbackVar.onDataError(it.localizedMessage)
                })


    }
}