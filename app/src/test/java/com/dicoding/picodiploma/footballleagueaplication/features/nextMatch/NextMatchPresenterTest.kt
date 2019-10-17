package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

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
import java.net.ConnectException

class NextMatchPresenterTest {

    @Mock
    private lateinit var view: NextMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var nextPresenter: NextMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        nextPresenter = NextMatchPresenter(view, gson, apiRepository, TestContextProvider())

    }

    @Test
    fun getNextMatchData() {
        val listNextMatch = mutableListOf<NextMatchItem>()
        val listTeamHome = mutableListOf<TeamDetailItem>()
        val listTeamAway = mutableListOf<TeamDetailItem>()
        val nextResponse = NextMatchResponse(listNextMatch)
        val homeNextResponse = TeamDetailResponse(listTeamHome)
        val awayNextResponse = TeamDetailResponse(listTeamAway)
        val idLeague = "4328"

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
            ).thenReturn(homeNextResponse)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(awayNextResponse)


            val listHomeBadge = mutableListOf<String>()
            val listAwayBadge = mutableListOf<String>()
            val listStadium = mutableListOf<String?>()
            val setDate = mutableSetOf<String>()

            homeNextResponse.teams.map {
                listHomeBadge.add(it.strTeamBadge)
                listStadium.add(it.strStadium)
            }

            awayNextResponse.teams.map {
                listAwayBadge.add(it.strTeamBadge)
            }

            nextResponse.events.map {
                setDate.add(it.dateEvent)
            }

            nextPresenter.getNextMatchData(idLeague)

            try {
                Mockito.verify(view).showLoading()
                Mockito.verify(view)
                    .loadDataToView(nextResponse.events, listHomeBadge, listAwayBadge, listStadium, setDate)
                Mockito.verify(view).hideLoading()
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }

    }
}