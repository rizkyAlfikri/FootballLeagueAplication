package com.dicoding.picodiploma.footballleagueaplication.features.standingsleague

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.StandingsRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class StandingsLeaguePresenterTest {

    @Mock
    private lateinit var view: StandingsLeagueView

    @Mock
    private lateinit var standingsRepository: StandingsRepository

    @Mock
    private lateinit var dataTable: List<Table>

    @Mock
    private lateinit var dataBadge: List<String>

    private lateinit var standingsLeaguePresenter: StandingsLeaguePresenter

    private val idLeague = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        standingsLeaguePresenter = StandingsLeaguePresenter(view, standingsRepository)
    }

    @Test
    fun getStandingLeagueSuccessTest() {

        standingsLeaguePresenter.getStandingLeague(idLeague)

        argumentCaptor<RepositoryCallbackTable<List<Table>, List<String>>>().apply {
            verify(standingsRepository).getStandingTable(eq(idLeague), capture())
            firstValue.onDataLoaded(dataTable, dataBadge)
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadTableDataToView(dataTable, dataBadge)
    }

    @Test
    fun getStandingLeagueErrorTest() {

        standingsLeaguePresenter.getStandingLeague("")

        argumentCaptor<RepositoryCallbackTable<List<Table>, List<String>>>().apply {
            verify(standingsRepository).getStandingTable(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }


}