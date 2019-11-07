package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NextMatchRepository {

    private val apiService = RetrofitService.createService(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getNextMatchData(
        idLeague: String?,
        multipleCallback: RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>
    ) {
        val listSetNext  = mutableSetOf<NextMatchItem>()
        val listBadge = mutableListOf<TeamDetailResponse>()
        val listHomeBadge = mutableListOf<TeamDetailResponse>()
        val listAwayBadge = mutableListOf<TeamDetailResponse>()

        apiService.getNextMatch(idLeague)
            .flatMapIterable { it.events }
            .flatMap (
                {
                    Observable.concat (
                        apiService.getTeamDetailFromServer(it.idHomeTeam),
                        apiService.getTeamDetailFromServer(it.idAwayTeam)
                    )
                }
            ,
                {
                    t1: NextMatchItem, t2: TeamDetailResponse -> Pair(t1, t2)
                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {

                listBadge.filterIndexed { index, _ ->
                    (index + 1) % 2 == 0
                }.map {
                    listAwayBadge.add(it)
                }

                listBadge.filterIndexed{ index, _ ->
                    (index + 1) % 2 != 0
                }.map {
                    listHomeBadge.add(it)
                }

                multipleCallback.onDataLoaded(listSetNext, listHomeBadge, listAwayBadge)


            }
            .subscribe(
                {
                    if (it != null) {
                        listSetNext.add(it.first)
                        listBadge.add(it.second)
                    } else {
                        multipleCallback.onDataError("Failed, next match data is null")
                    }
                }
            ,
                {
                    multipleCallback.onDataError("Failed request next match data to server")
                }
            )
    }
}