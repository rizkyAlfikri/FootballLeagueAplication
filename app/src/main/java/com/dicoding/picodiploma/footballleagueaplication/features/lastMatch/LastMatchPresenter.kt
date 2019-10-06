package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
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
        val listHome = mutableListOf<String>()
        val listAway = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()
        val setDate = mutableSetOf<String>()

        view.showLoading()

        doAsync {

            // get data past league match from server
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDb.getLastMatch(idLeague)),
                LastMatchResponse::class.java
            )
            val position = data.lastMatchItems.size
            listHome.clear()
            listAway.clear()
            listStadium.clear()

            repeat(position) {

                // get image badge home team from server
                val dataHome = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.lastMatchItems[it].idHomeTeam)),
                    TeamDetailResponse::class.java
                )

                // get image badge home team from server
                val dataAway = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.lastMatchItems[it].idAwayTeam)),
                    TeamDetailResponse::class.java
                )

                listHome.add(dataHome.teams[0].strTeamBadge)
                listAway.add(dataAway.teams[0].strTeamBadge)
                listStadium.add(dataHome.teams[0].strStadium)
            }

            // get date event from server and put them to Set collection
            setDate.clear()
            data.lastMatchItems.map {
                setDate.add(it.dateEvent)
            }

            uiThread {
                view.hideLoading()
                try {
                    // load to LastMatchFragment
                    view.loadLastMatch(data.lastMatchItems, listHome, listAway, listStadium, setDate)
                } catch (e: NullPointerException) {
                    view.onFailure(e)
                }
            }
        }
    }
}