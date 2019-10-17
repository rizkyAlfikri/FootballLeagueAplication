package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
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
import java.net.ConnectException

class LastMatchPresenterTest {

    @Mock
    private lateinit var view: LastMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var lastPresenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lastPresenter = LastMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLastMatchData() {
        val listLastMatch = mutableListOf<LastMatchItem>()
        val listTeamHome = mutableListOf<TeamDetailItem>()
        val listTeamAway = mutableListOf<TeamDetailItem>()
        val lastResponse = LastMatchResponse(listLastMatch)
        val homeResponse = TeamDetailResponse(listTeamHome)
        val awayResponse = TeamDetailResponse(listTeamAway)
        val idLeague = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LastMatchResponse::class.java
                )
            ).thenReturn(lastResponse )

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(homeResponse)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(awayResponse)

            val listBadgeHome = mutableListOf<String>()
            val listBadgeAway = mutableListOf<String>()
            val listStadium = mutableListOf<String?>()
            val setDate = mutableSetOf<String>()

            homeResponse.teams.forEach {
                listBadgeHome.add(it.strTeamBadge)
                listStadium.add(it.strStadium)
            }

            awayResponse.teams.forEach {
                listBadgeAway.add(it.strTeamBadge)
            }

            lastResponse.lastMatchItems.forEach {
                setDate.add(it.dateEvent)
            }

            lastPresenter.getLastMatchData(idLeague)

            try {
                Mockito.verify(view).showLoading()
                Mockito.verify(view)
                    .loadLastMatch(lastResponse.lastMatchItems, listBadgeHome, listBadgeAway, listStadium, setDate)
                Mockito.verify(view).hideLoading()
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }

    }
}