package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

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

class TeamDetailPresenterTest {

    @Mock
    private lateinit var view: TeamDetailView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var teamDetailResponse: TeamDetailResponse

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var teamDetailPresenter: TeamDetailPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        teamDetailPresenter = TeamDetailPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getTeamDetailData() {
        val idTeam = "133604"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await())
                .thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailResponse::class.java
                )
            ).thenReturn(teamDetailResponse)

            teamDetailPresenter.getTeamDetailData(idTeam)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()

            try {
                Mockito.verify(view).loadToView(teamDetailResponse.teams)
            } catch (e: ConnectException) {
                Mockito.verify(view).onFailure(e)
            }
        }
    }
}