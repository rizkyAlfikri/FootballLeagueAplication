package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer

import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamPlayerRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TeamPlayerPresenterTest {

    @Mock
    private lateinit var view: TeamPlayerView

    @Mock
    private lateinit var playerRepository: TeamPlayerRepository

    @Mock
    private lateinit var playerResponse: PlayerResponse

    private lateinit var teamPlayerPresenter: TeamPlayerPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        teamPlayerPresenter = TeamPlayerPresenter(view, playerRepository)
    }

    @Test
    fun getTeamPlayerSuccessTest() {
        val idTeam = "133604"
        val listTeamFormation = mutableSetOf<String>()

        teamPlayerPresenter.getPlayerFromServer(idTeam)

        argumentCaptor<RepositoryCallback<PlayerResponse>>().apply {
            verify(playerRepository).getTeamPlayer(eq(idTeam), capture())
            firstValue.onDataLoaded(playerResponse)
        }

        playerResponse.player.map {
            listTeamFormation.add(it.strPosition)
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadPlayerToView(listTeamFormation, playerResponse.player)
    }

    @Test
    fun getTeamPlayerErrorTest() {
        teamPlayerPresenter.getPlayerFromServer("")

        argumentCaptor<RepositoryCallback<PlayerResponse>>().apply {
            verify(playerRepository).getTeamPlayer(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}
