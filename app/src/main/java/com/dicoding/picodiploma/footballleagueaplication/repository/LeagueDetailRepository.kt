package com.dicoding.picodiploma.footballleagueaplication.repository

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LeagueDetailRepository {

    @SuppressLint("CheckResult")
    fun getLeagueDetail(idLeague: String?, callback: RepositoryCallback<LeagueDetailResponse>) {
        RetrofitService.createService(ApiService::class.java)
            .getLeagueDetailApi(idLeague)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (it != null) {
                        callback.onDataLoaded(it)
                    } else {
                        callback.onDataError("Failed, data is null")
                    }
            },
                {
                    callback.onDataError("Failed request data to server")
                })
    }
}