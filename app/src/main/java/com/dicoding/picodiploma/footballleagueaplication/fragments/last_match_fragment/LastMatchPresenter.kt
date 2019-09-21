package com.dicoding.picodiploma.footballleagueaplication.fragments.last_match_fragment

import com.dicoding.picodiploma.footballleagueaplication.models.last.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetail.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LastMatchPresenter(
    private val view: LastMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getLastMatchData(idLeague: String?) {
        val listHome: MutableList<String> = mutableListOf()
        val listAway: MutableList<String> = mutableListOf()
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDb.getLastMatch(idLeague)),
                LastMatchResponse::class.java
            )
            val position = data.lastMatchItems.size
            listHome.clear()
            listAway.clear()

            repeat(position) {
                val dataHome = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.lastMatchItems[it].idHomeTeam)),
                    TeamDetailResponse::class.java
                )

                val dataAway = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.lastMatchItems[it].idAwayTeam)),
                    TeamDetailResponse::class.java
                )

                listHome.add(dataHome.teams[0].strTeamBadge)
                listAway.add(dataAway.teams[0].strTeamBadge)
            }

            uiThread {
                view.hideLoading()
                try {
                    view.loadLastMatch(data.lastMatchItems)
                    view.loadHomeBadge(listHome)
                    view.loadAwayBadge(listAway)
                } catch (e: NullPointerException) {
                    view.onFailure(e)
                }
            }
        }
    }
}