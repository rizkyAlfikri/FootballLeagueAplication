package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamScheduleRepository {

    private val apiService = RetrofitService.createService(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getLastScheduleData(
        idLeague: String?,
        idTeam: String?,
        callback: RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>
    ) {

        val setLastSchedule = mutableSetOf<LastMatchItem>()
        val listBadge = mutableListOf<TeamDetailResponse>()
        val listHomeBadge = mutableListOf<TeamDetailResponse>()
        val listAwayBadge = mutableListOf<TeamDetailResponse>()

        apiService.getLastMatch(idLeague)
            .flatMapIterable { it.lastMatchItems }
            .filter { (it.idHomeTeam == idTeam) || (it.idAwayTeam == idTeam) }
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
                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                listBadge.filterIndexed { index, _ ->
                    (index + 1) % 2 != 0
                }.map {
                    listHomeBadge.add(it)
                }

                listBadge.filterIndexed { index, _ ->
                    (index + 1) % 2 == 0
                }.map {
                    listAwayBadge.add(it)
                }

                callback.onDataLoaded(setLastSchedule, listHomeBadge, listAwayBadge)

            }
            .subscribe(
                {
                    if (it != null) {
                        setLastSchedule.add(it.first)
                        listBadge.add(it.second)
                    } else {
                        callback.onDataError("Failed, last schedule data is null")
                    }
                }
                ,
                {
                    callback.onDataError(it.localizedMessage)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun getNextScheduleData(
        idLeague: String?,
        idTeam: String?,
        callback: RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>
    ) {

        val setNextSchedule = mutableSetOf<NextMatchItem>()
        val listBadge = mutableListOf<TeamDetailResponse>()
        val listHomeBadge = mutableListOf<TeamDetailResponse>()
        val listAwayBadge = mutableListOf<TeamDetailResponse>()

        apiService.getNextMatch(idLeague)
            .flatMapIterable { it.events }
            .filter { (it.idHomeTeam == idTeam) || (it.idAwayTeam) == idTeam }
            .flatMap(
                {
                    Observable.concat(
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
                listBadge.filterIndexed {
                        index, _ ->
                    (index + 1) % 2 != 0
                }.map {
                    listHomeBadge.add(it)
                }

                listBadge.filterIndexed {
                        index, _ ->
                    (index + 1) % 2 == 0
                }.map {
                    listAwayBadge.add(it)
                }

                callback.onDataLoaded(setNextSchedule, listHomeBadge, listAwayBadge)
            }
            .subscribe(
                {
                    if (it != null) {
                        setNextSchedule.add(it.first)
                        listBadge.add(it.second)
                    } else {
                        callback.onDataError("Failed, next schedule data is null")
                    }
                }
            ,
                {
                    callback.onDataError(it.localizedMessage)
                }
            )
    }
}