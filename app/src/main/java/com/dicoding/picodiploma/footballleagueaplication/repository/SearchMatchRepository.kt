package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import android.util.Log
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchMatchRepository(private val idLeague: String?) {

    private val apiService = RetrofitService.createService(ApiService::class.java)


    @SuppressLint("CheckResult")
    fun getSearchMatchData(
        query: String?,
        callback: RepositoryMultipleCallback<Set<SearchMatchItem>, List<TeamDetailResponse>>
    ) {
        val listSearchMatch = mutableSetOf<SearchMatchItem>()
        val listBadge = mutableListOf<TeamDetailResponse>()
        val listHomeBadge = mutableListOf<TeamDetailResponse>()
        val listAwayBadge = mutableListOf<TeamDetailResponse>()

        apiService.getSearchMatch(query)
            .flatMapIterable { it.event }
            .filter { it.idLeague == idLeague }
            .flatMap(
                {
                    Observable.concat(
                        apiService.getTeamDetailFromServer(it.idHomeTeam),
                        apiService.getTeamDetailFromServer(it.idAwayTeam)
                    )
                }
            ,
                {
                    t1: SearchMatchItem, t2: TeamDetailResponse -> Pair(t1, t2)
                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {

                listBadge.filterIndexed{ index, _ ->
                    (index + 1 ) % 2 != 0
                }.map {
                    listHomeBadge.add(it)
                }

                listBadge.filterIndexed{ index, _ ->
                    (index + 1) % 2 == 0
                }.map {
                    listAwayBadge.add(it)
                }
                callback.onDataLoaded(listSearchMatch, listHomeBadge, listAwayBadge)
            }
            .subscribe(
                {
                    if (it.second.teams.isNotEmpty()) {
                        listSearchMatch.add(it.first)
                        listBadge.add(it.second)

                        Log.e("Search", it.first.strEvent)
                    } else {
                        callback.onDataError("Failed request search match data to server")
                    }
                }
            ,
                {
                    callback.onDataError(it.localizedMessage)
                }
            )
    }
}