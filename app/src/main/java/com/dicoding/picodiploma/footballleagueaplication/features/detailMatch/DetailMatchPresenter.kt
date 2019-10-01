package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch

import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.NullPointerException
import java.net.ConnectException

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val gson: Gson,
    private val apiRepository: ApiRepository
) {

    fun getDetailMatchData(idEvent: String?) {
        view.showLoading()

        doAsync {

            // get data from server
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getDetailMatch(idEvent)),
                MatchDetailResponse::class.java
            )

            val idHome = data.events[0].idHomeTeam
            val idAway = data.events[0].idAwayTeam

            // get image badger team home from server
            val homeBadgeData = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getTeamDetail(idHome)),
                TeamDetailResponse::class.java
            )

            // get image badger team away from server
            val awayBadgeData = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getTeamDetail(idAway)),
                TeamDetailResponse::class.java
            )


            uiThread {
                view.hideLoading()

                try {
                    // load data to activity
                    view.loadDataToView(data.events)
                    view.loadHomeBadgeToView(homeBadgeData.teams[0].strTeamBadge)
                    view.loadAwayBadgeToView(awayBadgeData.teams[0].strTeamBadge)
                } catch (e: NullPointerException) {
                    view.onFailure(e)
                } catch (e: ConnectException) {
                    view.onFailure(e)
                }
            }
        }
    }
}