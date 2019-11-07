package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LastMatchRepository {

    private val apiService = RetrofitService.createService(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getLastMatchData(
        idLeague: String?,
        multipleCallback: RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>
    ) {
        val listLastSet = mutableSetOf<LastMatchItem>()
        val listBadge = mutableListOf<TeamDetailResponse>()
        val listAwayBadge = mutableListOf<TeamDetailResponse>()
        val listHomeBadge = mutableListOf<TeamDetailResponse>()

        apiService.getLastMatch(idLeague)
            .flatMapIterable { it.lastMatchItems }
            .flatMap(
                {
                    Observable.concat(
                        apiService.getTeamDetailFromServer(it.idHomeTeam),
                        apiService.getTeamDetailFromServer(it.idAwayTeam)
                    )
                }
                ,
                { t1: LastMatchItem, t2: TeamDetailResponse ->
                    Pair(t1, t2)
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {

                listBadge.filterIndexed { index, _ ->
                    (index + 1) % 2 == 0
                }.map {
                    listAwayBadge.add(it)
                }

                listBadge.filterIndexed { index, _ ->
                    (index + 1) % 2 != 0
                }.map {
                    listHomeBadge.add(it)
                }

                multipleCallback.onDataLoaded(listLastSet, listHomeBadge, listAwayBadge)

            }
            .subscribe(
                { itResponse ->
                    if (itResponse != null) {
                        listBadge.add(itResponse.second)
                        listLastSet.add(itResponse.first)
                    } else {
                        multipleCallback.onDataError("Failed, last match data is null")
                    }
                }
                ,
                {
                    multipleCallback.onDataError("Failed request last match data to server")
                }
            )

    }
}