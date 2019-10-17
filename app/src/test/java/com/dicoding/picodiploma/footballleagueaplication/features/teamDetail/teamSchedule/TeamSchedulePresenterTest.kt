package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test


import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.NullPointerException
import java.net.ConnectException

class TeamSchedulePresenterTest {

    @Mock
    private lateinit var view: TeamScheduleView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var schedulePresenter: TeamSchedulePresenter
    private lateinit var idLeague: String
    private lateinit var idTeam: String

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulePresenter = TeamSchedulePresenter(view, gson, apiRepository, TestContextProvider())
        idLeague = "4328"
        idTeam = "133604"
    }

    @Test
    fun getLastMatchData() {
        val listLastMatchItem = mutableListOf<LastMatchItem>()
        val listLastTeamHome = mutableListOf<TeamDetailItem>()
        val listLastTeamAway = mutableListOf<TeamDetailItem>()
        val lastResponse = LastMatchResponse(listLastMatchItem)
        val lastTeamHomeResponse = TeamDetailResponse(listLastTeamHome)
        val lastTeamAwayResponse = TeamDetailResponse(listLastTeamAway)


        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LastMatchResponse::class.java
                )
            ).thenReturn(lastResponse)


            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(lastTeamHomeResponse)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(lastTeamAwayResponse)

            val listLastMatch = mutableListOf<LastMatchItem>()
            val listHomeBadge = mutableListOf<String>()
            val listAwayBadge = mutableListOf<String>()
            val listLastStadium = mutableListOf<String?>()


            lastResponse.lastMatchItems.forEach {
                if (idTeam == it.idHomeTeam || idTeam == it.idAwayTeam) {
                    listLastMatch.add(it)
                }
            }

            lastTeamHomeResponse.teams.forEach {
                listHomeBadge.add(it.strTeamBadge)
                listLastStadium.add(it.strStadium)
            }

            lastTeamAwayResponse.teams.forEach {
                listAwayBadge.add(it.strTeamBadge)
            }

            schedulePresenter.getLastMatchData(idLeague, idTeam)

            try {
                Mockito.verify(view).showLoading()
                Mockito.verify(view).hideLoading()
                Mockito.verify(view).loadLastTeam(listLastMatch, listHomeBadge, listAwayBadge, listLastStadium)
            } catch (e: NullPointerException) {
                Mockito.verify(view).onFailure(e)
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }
    }


    @Test
    fun getNextMatchData() {
        val listNextMatchItem = mutableListOf<NextMatchItem>()
        val listNextTeamHome = mutableListOf<TeamDetailItem>()
        val listNextTeamAway = mutableListOf<TeamDetailItem>()
        val nextResponse = NextMatchResponse(listNextMatchItem)
        val nextTeamHomeResponse = TeamDetailResponse(listNextTeamHome)
        val nextTeamAwayResponse = TeamDetailResponse(listNextTeamAway)

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    NextMatchResponse::class.java
                )
            ).thenReturn(nextResponse)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(nextTeamHomeResponse)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(nextTeamAwayResponse)

            val listNextMatch = mutableListOf<NextMatchItem>()
            val listHomeBadge = mutableListOf<String>()
            val listAwayBadge = mutableListOf<String>()
            val listNextStadium = mutableListOf<String?>()

            nextResponse.events.forEach {
                if (idTeam == it.idHomeTeam || idTeam == it.idAwayTeam) {
                    listNextMatch.add(it)
                }
            }

            nextTeamHomeResponse.teams.forEach {
                listHomeBadge.add(it.strTeamBadge)
                listNextStadium.add(it.strStadium)
            }

            nextTeamAwayResponse.teams.forEach {
                listAwayBadge.add(it.strTeamBadge)
            }

            schedulePresenter.getNextMatchData(idLeague, idTeam)

            try {
                Mockito.verify(view).showLoading()
                Mockito.verify(view).hideLoading()
                Mockito.verify(view).loadNextTeam(listNextMatch, listHomeBadge, listAwayBadge, listNextStadium)
            } catch (e: NullPointerException) {
                Mockito.verify(e)
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }
    }
}