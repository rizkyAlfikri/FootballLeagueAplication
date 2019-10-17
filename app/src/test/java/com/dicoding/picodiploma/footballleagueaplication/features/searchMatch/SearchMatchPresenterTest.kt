package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch

import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchResponse
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

class SearchMatchPresenterTest {

    @Mock
    private lateinit var view: SearchMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var searchPresenter: SearchMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchMatchPresenter(view, gson, apiRepository, TestContextProvider())
    }


    @Test
    fun getSearchMatchData() {
        val listSearch = mutableListOf<SearchMatchItem>()
        val listHomeTeam = mutableListOf<TeamDetailItem>()
        val listAwayTeam = mutableListOf<TeamDetailItem>()
        val searchResponse = SearchMatchResponse(listSearch)
        val homeResponse = TeamDetailResponse(listHomeTeam)
        val awayResponse = TeamDetailResponse(listAwayTeam)
        val query = "Arsenal"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    SearchMatchResponse::class.java
                )
            ).thenReturn(searchResponse)

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

            val searchData = mutableListOf<SearchMatchItem>()
            val homeBadge = mutableListOf<String>()
            val awayBadge = mutableListOf<String>()
            val listStadium = mutableListOf<String?>()

            searchResponse.event.forEach {
                if (it.strSport == "Soccer") {
                    searchData.add(it)
                }
            }

            homeResponse.teams.forEach {
                homeBadge.add(it.strTeamBadge)
                listStadium.add(it.strStadium)
            }

            awayResponse.teams.forEach {
                awayBadge.add(it.strTeamBadge)
            }

            searchPresenter.getSearchMatchData(query)

            try {
                Mockito.verify(view).showLoading()
                Mockito.verify(view).hideLoading()
                Mockito.verify(view).loadDataToView(searchData, homeBadge, awayBadge, listStadium)
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }

        }


    }
}