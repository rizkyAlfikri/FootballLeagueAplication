package com.dicoding.picodiploma.footballleagueaplication.features.teams

import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.ConnectException

class TeamPresenterTest {

    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var teamResponse: TeamResponse

    @Mock
    private lateinit var gson: Gson
    
    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var teamPresenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getTeamDataLoadedTest() {
        val listTeamBadge = mutableListOf<String>()
        val listTeamName = mutableListOf<String>()
        val listTeamId = mutableListOf<String>()
        val idLeague = "4328"

        runBlocking {

            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            teamResponse.teams.map {
                listTeamBadge.add(it.strTeamBadge)
                listTeamName.add(it.strTeam)
                listTeamId.add(it.idTeam)
            }

            teamPresenter.getTeamData(idLeague)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()
            
            try {
                Mockito.verify(view).loadDataToView(listTeamBadge, listTeamName, listTeamId)
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }
    }
}