package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch

import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.dicoding.picodiploma.footballleagueaplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import java.net.ConnectException

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val gson: Gson,
    private val apiRepository: ApiRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getDetailMatchData(idEvent: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {

            // get data from server
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getDetailMatch(idEvent)).await(),
                MatchDetailResponse::class.java
            )

            var idHome = ""
            var idAway = ""

            data.events.forEach {

                idHome = it.idHomeTeam
                idAway = it.idAwayTeam

            }

            // get image badger team home from server
            val homeBadgeData = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getTeamDetail(idHome)).await(),
                TeamDetailResponse::class.java
            )

            // get image badger team away from server
            val awayBadgeData = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getTeamDetail(idAway)).await(),
                TeamDetailResponse::class.java
            )

            var homeBadge = ""
            var awayBadge = ""

            homeBadgeData.teams.forEach {
                homeBadge = it.strTeamBadge
            }

            awayBadgeData.teams.forEach {
                awayBadge = it.strTeamBadge
            }

            view.hideLoading()

            try {
                // load data to activity
                view.loadDataToView(data.events)
                view.loadHomeBadgeToView(homeBadge)
                view.loadAwayBadgeToView(awayBadge)
            } catch (e: NullPointerException) {
                view.onFailure(e)
            } catch (e: ConnectException) {
                view.onFailure(e)
            }
        }
    }
}