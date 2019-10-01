package com.dicoding.picodiploma.footballleagueaplication.features.home

import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.ConnectException


class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val gson: Gson,
    private val apiRepository: ApiRepository
) {

    fun getLeagueDetail(idLeague: String?) {
        var listBanner = mutableListOf<String>()

        view.showLoading()

        doAsync {
            // get data detail league from server
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDb.getDetailLeague(idLeague)),
                LeagueDetailResponse::class.java
            )

            // collect image banner
            data.leagues[0].apply {
                listBanner.clear()
                listBanner = mutableListOf(
                    strBadge, strPoster, strFanart1, strFanart2, strFanart3, strFanart4, strTrophy
                )

            }

            uiThread {
                view.hideLoading()

                try {
                    // load data to home fragment
                    view.loadDataToView(data)
                    view.loadBannerToView(listBanner)
                } catch (e: ConnectException) {
                    view.onFailure(e)
                } catch (e: NullPointerException) {
                    view.onFailure(e)
                }
            }
        }
    }
}