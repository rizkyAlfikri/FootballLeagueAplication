package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamRepository {

    @SuppressLint("CheckResult")
    fun getTeamList(idLeague: String?, callback: RepositoryCallback<TeamResponse>) {

        RetrofitService.createService(ApiService::class.java)
            .getTeamFromServer(idLeague)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    callback.onDataLoaded(it)
                } else {
                    callback.onDataError("Failed, team data is null")
                }
            },
                {
                    callback.onDataError("Failed request team data to server")
                })
    }
}