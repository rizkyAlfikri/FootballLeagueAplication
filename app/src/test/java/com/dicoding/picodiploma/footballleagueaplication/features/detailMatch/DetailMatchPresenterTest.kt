package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch

import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
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

class DetailMatchPresenterTest {

    @Mock
    private lateinit var view: DetailMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var detaMatchPresenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detaMatchPresenter = DetailMatchPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getDetailMatchData() {
        val listMatch = mutableListOf<MatchDetailItem>()
        val listTeamHome = mutableListOf<TeamDetailItem>()
        val listTeamAway = mutableListOf<TeamDetailItem>()
        val matchResponse = MatchDetailResponse(listMatch)
        val homeResponse = TeamDetailResponse(listTeamHome)
        val awayResponse = TeamDetailResponse(listTeamAway)
        val idEvent = "441613"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchDetailResponse::class.java
                )
            ).thenReturn(matchResponse)

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

            var homeBadge = ""
            var awayBadge = ""

            homeResponse.teams.forEach {
                homeBadge = it.strTeamBadge
            }

            awayResponse.teams.forEach {
                awayBadge = it.strTeamBadge
            }


            detaMatchPresenter.getDetailMatchData(idEvent)

            try {
                Mockito.verify(view).showLoading()
                Mockito.verify(view).loadDataToView(matchResponse.events)
                Mockito.verify(view).loadHomeBadgeToView(homeBadge)
                Mockito.verify(view).loadAwayBadgeToView(awayBadge)
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }
    }
}