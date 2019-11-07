package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchTeamRepository {
    private val apiService = RetrofitService.createService(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getSearchTeamData(
        query: String?,
        idLeague: String?,
        callbackTable: RepositoryCallbackTable<Set<Table>, Set<SearchTeamItem>>
    ) {

        val listSetSearchTeam = mutableSetOf<SearchTeamItem>()
        val listSetTableTeam = mutableSetOf<Table>()

        apiService.getSearchTeamApi(query)
            .flatMapIterable { it.teams }
            .filter { it.idLeague == idLeague }
            .flatMap(
                { item ->
                    apiService.getTableTeam(item.idLeague).flatMapIterable { it.table }
                        .filter { it.teamid == item.idTeam }
                }
                ,
                {
                    t1: SearchTeamItem, t2: Table ->  Pair(t1, t2)
                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { callbackTable.onDataLoaded(listSetTableTeam, listSetSearchTeam)}
            .subscribe(
                {
                    if (it != null) {
                        listSetSearchTeam.add(it.first)
                        listSetTableTeam.add(it.second)
                    } else {
                        callbackTable.onDataError("Failed, search team data is null")
                    }
                }
                ,
                {
                    callbackTable.onDataError(it.localizedMessage)
                }
            )
    }
}