package com.dicoding.picodiploma.footballleagueaplication.features.searchteams

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamItem
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallbackTable
import com.dicoding.picodiploma.footballleagueaplication.repository.SearchTeamRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchTeamPresenterTest {

    @Mock
    private lateinit var view: SearchTeamView

    @Mock
    private lateinit var searchTeamRepository: SearchTeamRepository

    private lateinit var searchTeamPresenter: SearchTeamPresenter

    private val idLeague = "4328"

    private val query = "Arsenal"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchTeamPresenter = SearchTeamPresenter(view, searchTeamRepository)
    }

    @Test
    fun getSearchTeamSuccessTest() {
        val setTable = mutableSetOf<Table>()
        val setTeam = mutableSetOf<SearchTeamItem>()
        val listTeam = mutableListOf<SearchTeamItem>()
        val listTable = mutableListOf<Table>()

        searchTeamPresenter.getSearchTeam(query, idLeague)

        argumentCaptor<RepositoryCallbackTable<Set<Table>, Set<SearchTeamItem>>>().apply {
            verify(searchTeamRepository).getSearchTeamData(eq(query), eq(idLeague), capture())
            firstValue.onDataLoaded(setTable, setTeam)
        }

        listTeam.addAll(setTeam)
        listTable.addAll(setTable)

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadDataToView(listTable, listTeam)
    }

    @Test
    fun getSearchTeamErrorTest() {
        searchTeamPresenter.getSearchTeam("", "")

        argumentCaptor<RepositoryCallbackTable<Set<Table>, Set<SearchTeamItem>>>().apply {
            verify(searchTeamRepository).getSearchTeamData(eq(""), eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")

    }
}