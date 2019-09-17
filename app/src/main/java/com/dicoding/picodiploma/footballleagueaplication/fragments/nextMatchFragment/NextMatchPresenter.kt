package com.dicoding.picodiploma.footballleagueaplication.fragments.nextMatchFragment

import com.dicoding.picodiploma.footballleagueaplication.models.nextMatch.NextMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetail.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.NullPointerException
import java.net.ConnectException

class NextMatchPresenter(
    private val view: NextMatchView,
    private val gson: Gson,
    private val apiRepository: ApiRepository
) {

    fun getNextMatchData(idLeague: String) {
        val listHome: MutableList<String> = mutableListOf()
        val listAway: MutableList<String> = mutableListOf()

        view.showLoading()

        doAsync {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getNextMatch(idLeague)),
                NextMatchResponse::class.java
            )

            val position = data.events.size
            listHome.clear()
            listAway.clear()

            repeat(position) {
                val dataHome = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.events[it].idHomeTeam)),
                    TeamDetailResponse::class.java
                )

                val dataAway = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.events[it].idAwayTeam)),
                    TeamDetailResponse::class.java
                )

                listHome.add(dataHome.teams[0].strTeamBadge)
                listAway.add(dataAway.teams[0].strTeamBadge)
            }

            uiThread {
                view.hideLoading()

                try {
                    view.loadDataToView(data.events)
                    view.loadHomeBadge(listHome)
                    view.loadAwayBadge(listAway)
                } catch (e: ConnectException) {
                    view.onFailure(e)
                } catch (e: NullPointerException) {
                    view.onFailure(e)
                }

            }
        }
    }
}