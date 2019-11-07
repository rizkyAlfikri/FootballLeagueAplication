package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlayerDetailRepository {

    @SuppressLint("CheckResult")
    fun getPlayerDetail(idPlayer: String?, callback: RepositoryCallback<PlayerDetailResponse>) {

        RetrofitService.createService(ApiService::class.java)
            .getPlayerDetailApi(idPlayer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    callback.onDataLoaded(it)
                } else {
                    callback.onDataError("Failed, player detail data is null")
                }
            },
                {
                    callback.onDataError("Failed request player detail data to server")
                })
    }

    @SuppressLint("CheckResult")
    fun getPlayerHonour(idPlayer: String?, callback: RepositoryCallback<PlayerHonorsResponse>) {

        RetrofitService.createService(ApiService::class.java)
            .getPlayerHonorsApi(idPlayer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it != null) {
                        callback.onDataLoaded(it)
                    } else {
                        callback.onDataError("Failed, player honor data is null")
                    }
                }
                ,
                {
                    callback.onDataError("Failed request player honor data to server")
                })
    }
}