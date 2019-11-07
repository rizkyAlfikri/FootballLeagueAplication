package com.dicoding.picodiploma.footballleagueaplication.features.teams

import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var teamResponse: TeamResponse

    @Mock
    private lateinit var teamRepository: TeamRepository

    private lateinit var teamPresenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(view, teamRepository)
    }

    @Test
    fun getTeamListSuccessTest() {
        val idLeague = "4328"

        teamPresenter.getTeamData(idLeague)

        argumentCaptor<RepositoryCallback<TeamResponse>>().apply {
            verify(teamRepository).getTeamList(eq(idLeague), capture())
            firstValue.onDataLoaded(teamResponse)
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadDataToView(teamResponse)
    }

    @Test
    fun getTeamListErrorTest() {
        teamPresenter.getTeamData("")

        argumentCaptor<RepositoryCallback<TeamResponse>>().apply {
            verify(teamRepository).getTeamList(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}