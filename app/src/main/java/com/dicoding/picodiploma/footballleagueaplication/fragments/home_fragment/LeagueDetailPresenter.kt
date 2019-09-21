package com.dicoding.picodiploma.footballleagueaplication.fragments.home_fragment

import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetail.LeagueDetailResponse
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
            val data = gson.fromJson(apiRepository.doRequest(TheSportDb.getDetailLeague(idLeague)),
            LeagueDetailResponse::class.java)

            data.leagues[0].apply {
                listBanner.clear()
                listBanner = mutableListOf(
                    strBadge, strPoster, strFanart1, strFanart2, strFanart3, strFanart4, strTrophy
                )

            }

            /*

            val listBanner = BannerModel(
                data.leagues[0].strBadge,
                data.leagues[0].strPoster,
                data.leagues[0].strFanart1,
                data.leagues[0].strFanart2,
                data.leagues[0].strFanart3,
                data.leagues[0].strFanart4,
                data.leagues[0].strTrophy
            )

             */

            uiThread {
                view.hideLoading()

                try {
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