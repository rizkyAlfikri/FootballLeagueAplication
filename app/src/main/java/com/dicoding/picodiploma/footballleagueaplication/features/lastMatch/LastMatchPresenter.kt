package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.dicoding.picodiploma.footballleagueaplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LastMatchPresenter(
    private val view: LastMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLastMatchData(idLeague: String?) {
        val listHome = mutableListOf<String>()
        val listAway = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()
        val setDate = mutableSetOf<String>()

        view.showLoading()

        GlobalScope.launch(context.main) {

            // get data past league match from server
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDb.getLastMatch(idLeague)).await(),
                LastMatchResponse::class.java
            )
            val position = data.lastMatchItems.size
            listHome.clear()
            listAway.clear()
            listStadium.clear()

            repeat(position) {

                // get image badge home team from server
                val dataHome = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.lastMatchItems[it].idHomeTeam)).await(),
                    TeamDetailResponse::class.java
                )

                // get image badge home team from server
                val dataAway = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.lastMatchItems[it].idAwayTeam)).await(),
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

            view.hideLoading()
            try {
                // load to LastMatchFragment
                view.loadLastMatch(data.lastMatchItems, listHome, listAway, listStadium, setDate)
            } catch (e: NullPointerException) {
                view.onFailure("Koneksi Bermasalah")
            }
        }
    }
}