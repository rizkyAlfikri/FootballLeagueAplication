package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamPlayerRepository {

    @SuppressLint("CheckResult")
    fun getTeamPlayer(idTeam: String?, callback: RepositoryCallback<PlayerResponse>) {

        RetrofitService.createService(ApiService::class.java)
            .getPlayer(idTeam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    callback.onDataLoaded(it)
                } else {
                    callback.onDataError("Failed, player data is null")
                }
            },
                {
                    callback.onDataError("Failed request player data to server")
                })
    }
}