package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer

import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.TestContextProvider
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import rx.schedulers.TestScheduler

class TeamPlayerPresenterTest {

    @Mock
    private lateinit var view: TeamPlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var playerResponse: PlayerResponse

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var teamPlayerPresenter: TeamPlayerPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        teamPlayerPresenter = TeamPlayerPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getPlayerFromServer() {
        val idTeam = "133604"
        val teamFormation = mutableSetOf<String>()


        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await())
                .thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    PlayerResponse::class.java
                )
            ).thenReturn(playerResponse)

            teamPlayerPresenter.getPlayerFromServer(idTeam)

            val test = TestScheduler()
            test.triggerActions()

            playerResponse.player.map {
                teamFormation.add(it.strPosition)
            }

            verify(view).showLoading()
            verify(view).hideLoading()
            verify(view).loadPlayerToView(teamFormation, playerResponse.player)
        }
    }
}