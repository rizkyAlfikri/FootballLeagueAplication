package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailMatchRepository {

    private val apiService = RetrofitService.createService(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getDetailMatch(idEvent: String?, callback: RepositoryCallback<MatchDetailResponse>) {

        apiService.getMatchDetailApi(idEvent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    callback.onDataLoaded(it)
                } else {
                    callback.onDataError("Failed, data is null")
                }
            },
                {
                    callback.onDataError("Failed request detail match data to server")
                })
    }

    @SuppressLint("CheckResult")
    fun getHomeTeamBadge(idEvent: String?, callback: RepositoryCallback<TeamDetailResponse>) {
        val listTeamHome = mutableListOf<TeamDetailResponse>()

        apiService.getMatchDetailApi(idEvent)
            .flatMap { Observable.fromIterable(it.events) }
            .flatMap { apiService.getTeamDetailFromServer(it.idHomeTeam) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                listTeamHome.map {
                    callback.onDataLoaded(it)
                }
            }
            .subscribe({
                if (it != null) {
                    listTeamHome.add(it)
                } else {
                    callback.onDataError("Failed, data is null")
                }
            },
                {
                    callback.onDataError("Failed request team home badge to server")
                })
    }

    @SuppressLint("CheckResult")
    fun getAwayTeamBadge(idEvent: String?, callback: RepositoryCallback<TeamDetailResponse>) {
        val listTeamAway = mutableListOf<TeamDetailResponse>()

        apiService.getMatchDetailApi(idEvent)
            .flatMap { Observable.fromIterable(it.events) }
            .flatMap { apiService.getTeamDetailFromServer(it.idAwayTeam) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                listTeamAway.map { callback.onDataLoaded(it) }
            }
            .subscribe({
                if (it != null) {
                    listTeamAway.add(it)
                } else {
                    callback.onDataError("Failed, data is null")
                }
            },
                {
                    callback.onDataError("Failed request team away badge data to server")
                })
    }
}