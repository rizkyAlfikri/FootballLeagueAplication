package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
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

    fun getNextMatchData(idLeague: String?) {
        val listHome = mutableListOf<String>()
        val listAway = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()
        val setDate = mutableSetOf<String>()

        view.showLoading()

        doAsync {

            // get next match data from server
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getNextMatch(idLeague)),
                NextMatchResponse::class.java
            )

            val position = data.events.size
            listHome.clear()
            listAway.clear()
            listStadium.clear()

            repeat(position) {

                // get image badge home team from server
                val dataHome = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.events[it].idHomeTeam)),
                    TeamDetailResponse::class.java
                )

                // get image badge home team from server
                val dataAway = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(data.events[it].idAwayTeam)),
                    TeamDetailResponse::class.java
                )

                listHome.add(dataHome.teams[0].strTeamBadge)
                listAway.add(dataAway.teams[0].strTeamBadge)
                listStadium.add(dataHome.teams[0].strStadium)
            }

            // get date event from server and put them to Set collection
            setDate.clear()
            data.events.map {
                setDate.add(it.dateEvent)
            }
            uiThread {
                view.hideLoading()

                try {
                    // load data to NextMatchPresenter
                    view.loadDataToView(data.events, listHome, listAway, listStadium, setDate)
                } catch (e: ConnectException) {
                    view.onFailure(e)
                } catch (e: NullPointerException) {
                    view.onFailure(e)
                }

            }
        }
    }
}