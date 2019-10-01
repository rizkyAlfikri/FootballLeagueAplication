package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch

import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.HttpException
import kotlin.NullPointerException

class SearchMatchPresenter(
    private val view: SearchMatchView,
    private val gson: Gson,
    private val apiRepository: ApiRepository
) {

    fun getSearchMatchData(query: String?) {
        val listHome: MutableList<String> = mutableListOf()
        val listAway: MutableList<String> = mutableListOf()
        val listData: MutableList<SearchMatchItem> = mutableListOf()
        view.showLoading()

        doAsync {

            // get search match data from server
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDb.getSearchMatch(query)),
                SearchMatchResponse::class.java
            )

            // filter data, where data corresponding to Soccer will be add in collection list
            listData.clear()
            for (position in data.event.indices) {
                if (data.event[position].strSport == "Soccer") {
                    listData.add(data.event[position])
                }
            }

            listHome.clear()
            listAway.clear()
            for (position in listData.indices) {

                // get image badget home team from server
                val dataHome = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDb.getTeamDetail(data.event[position].idHomeTeam)),
                    TeamDetailResponse::class.java
                )

                // get image badget home team from server
                val dataAway = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDb.getTeamDetail(data.event[position].idAwayTeam)),
                    TeamDetailResponse::class.java
                )

                listHome.add(dataHome.teams[0].strTeamBadge)
                listAway.add(dataAway.teams[0].strTeamBadge)

            }

            uiThread {
                view.hideLoading()
                try {
                    // load data to search activity
                    view.loadDataToView(listData, listHome, listAway)
                } catch (e: HttpException) {
                    view.onFailure(e)
                } catch (e: NullPointerException) {
                    view.onFailure(e)
                }
            }
        }
    }
}