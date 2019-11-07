package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamDetailRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class   TeamDetailPresenterTest {

    @Mock
    private lateinit var view: TeamDetailView

    @Mock
    private lateinit var teamDetailRepository: TeamDetailRepository

    private lateinit var teamDetailPresenter: TeamDetailPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        teamDetailPresenter = TeamDetailPresenter(view, teamDetailRepository)
    }

    @Test
    fun getTeamDetailSuccessTest() {
        val idTeam = "133604"
        val idLeague = "4328"
        val dataTable = mutableListOf<Table>()
        val dataTeamBadge = mutableListOf<TeamDetailItem>()
        val listTeamBadge = mutableListOf<TeamDetailItem>()

        teamDetailPresenter.getTeamDetailData(idTeam, idLeague)

        argumentCaptor<RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>>().apply {
            verify(teamDetailRepository).getTeamDetail(eq(idTeam), eq(idLeague), capture())
            firstValue.onDataLoaded(dataTable, dataTeamBadge)
        }

        dataTeamBadge.map {
            listTeamBadge
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadToView(dataTable, dataTeamBadge)
    }

    @Test
    fun getTeamDetailErrorTest() {
        teamDetailPresenter.getTeamDetailData("", "")

        argumentCaptor<RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>>().apply {
            verify(teamDetailRepository).getTeamDetail(eq(""), eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}