package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamOverviewRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TeamOverviewPresenterTest {

    @Mock
    private lateinit var view: TeamOverviewView


    @Mock
    private lateinit var teamOverviewRepository: TeamOverviewRepository

    private lateinit var teamOverviewPresenter: TeamOverviewPresenter

    private val idTeam = "133604"
    private val idLeague = "4328"


    @Before
    fun setUpt() {
        MockitoAnnotations.initMocks(this)
        teamOverviewPresenter = TeamOverviewPresenter(view, teamOverviewRepository)
    }

    @Test
    fun getTeamOverviewDataSuccessTest() {
        val dataTable = mutableListOf<Table>()

        val dataTeam = mutableListOf<TeamDetailItem>()


        teamOverviewPresenter.getTeamOverviewData(idLeague, idTeam)

        argumentCaptor<RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>>().apply {
            verify(teamOverviewRepository).getTeamOverview(eq(idTeam), eq(idLeague), capture())
            firstValue.onDataLoaded(dataTable, dataTeam)
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadTeamOverviewToView(dataTable, dataTeam)
    }

    @Test
    fun getTeamOverviewErrorTest() {
        teamOverviewPresenter.getTeamOverviewData("", "")

        argumentCaptor<RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>>().apply {
            verify(teamOverviewRepository).getTeamOverview(eq(""), eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}