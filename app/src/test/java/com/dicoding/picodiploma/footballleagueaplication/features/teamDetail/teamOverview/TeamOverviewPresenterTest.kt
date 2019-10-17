package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
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

class TeamOverviewPresenterTest {

    @Mock
    private lateinit var view: TeamOverviewView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var teamDetailResponse: TeamDetailResponse

    @Mock
    private lateinit var teamTableResponse: TeamTableResponse

    private lateinit var overviewPresenter: TeamOverviewPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        overviewPresenter = TeamOverviewPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getTeamOverviewData() {
        val idLeague = "4328"
        val idSeason = "1213"
        val idTeam = "133612"
        val listTable = mutableListOf<Table>()


        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(teamDetailResponse)


            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamTableResponse::class.java
                )
            ).thenReturn(teamTableResponse)


            teamTableResponse.table.map {
                if (idTeam == it.teamid) {
                    listTable.add(it)
                }
            }

            overviewPresenter.getTeamOverviewData(idLeague, idSeason, idTeam)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()

            try {
                Mockito.verify(view).loadTeamDetailToView(teamDetailResponse.teams)
                Mockito.verify(view).loadTeamTableToView(listTable)
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }
    }
}