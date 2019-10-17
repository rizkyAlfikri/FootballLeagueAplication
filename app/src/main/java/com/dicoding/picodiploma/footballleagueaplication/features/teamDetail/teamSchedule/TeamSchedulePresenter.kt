package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.dicoding.picodiploma.footballleagueaplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import java.net.ConnectException

class TeamSchedulePresenter(
    private val view: TeamScheduleView,
    private val gson: Gson,
    private val apiRepository: ApiRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLastMatchData(idLeague: String?, idTeam: String?) {
        view.showLoading()

        val listLastMatch = mutableListOf<LastMatchItem>()
        val listLastHomeBadge = mutableListOf<String>()
        val listLastAwayBadge = mutableListOf<String>()
        val listLastStadium = mutableListOf<String?>()


        GlobalScope.launch(context.main) {
            val lastData = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getLastMatch(idLeague)).await(),
                LastMatchResponse::class.java
            )

            listLastMatch.clear()
            lastData.lastMatchItems.map {
                if (it.idHomeTeam == idTeam || it.idAwayTeam == idTeam) {
                    listLastMatch.add(it)
                }
            }

            listLastHomeBadge.clear()
            listLastAwayBadge.clear()
            listLastStadium.clear()

            for (position in listLastMatch.indices) {
                val homeBadgeData = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(listLastMatch[position].idHomeTeam)).await(),
                    TeamDetailResponse::class.java
                )

                val awayBadgeData = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(listLastMatch[position].idAwayTeam)).await(),
                    TeamDetailResponse::class.java
                )

                listLastHomeBadge.add(homeBadgeData.teams[0].strTeamBadge)
                listLastAwayBadge.add(awayBadgeData.teams[0].strTeamBadge)
                listLastStadium.add(homeBadgeData.teams[0].strStadium)

            }

            view.hideLoading()
            try {
                view.loadLastTeam(listLastMatch, listLastHomeBadge, listLastAwayBadge, listLastStadium)

            } catch (e: NullPointerException) {
                view.onFailure(e)
            } catch (e: ConnectException) {
                view.onFailure(e)
            }
        }
    }


    fun getNextMatchData(idLeague: String?, idTeam: String?) {
        val listNextMatch = mutableListOf<NextMatchItem>()
        val listNextHomeBadge = mutableListOf<String>()
        val listNextAwayBadge = mutableListOf<String>()
        val listNextStadium = mutableListOf<String?>()

        view.showLoading()

        GlobalScope.launch(context.main) {
            val nextData = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getNextMatch(idLeague)).await(),
                NextMatchResponse::class.java
            )

            listNextMatch.clear()
            nextData.events.map {
                if (it.idHomeTeam == idTeam || it.idAwayTeam == idTeam) {
                    listNextMatch.add(it)
                }
            }

            listNextHomeBadge.clear()
            listNextAwayBadge.clear()
            listNextStadium.clear()

            for (position in listNextMatch.indices) {
                val homeNextBadge = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(listNextMatch[position].idHomeTeam)).await(),
                    TeamDetailResponse::class.java
                )

                val awayNextBadge = gson.fromJson(
                    apiRepository.doRequest(TheSportDb.getTeamDetail(listNextMatch[position].idAwayTeam)).await(),
                    TeamDetailResponse::class.java
                )

                listNextHomeBadge.add(homeNextBadge.teams[0].strTeamBadge)
                listNextAwayBadge.add(awayNextBadge.teams[0].strTeamBadge)
                listNextStadium.add(homeNextBadge.teams[0].strStadium)
            }

            view.hideLoading()
            try {
                view.loadNextTeam(listNextMatch, listNextHomeBadge, listNextAwayBadge, listNextStadium)
            } catch (e: NullPointerException) {
                view.onFailure(e)
            } catch (e: ConnectException) {
                view.onFailure(e)
            }
        }
    }
}
