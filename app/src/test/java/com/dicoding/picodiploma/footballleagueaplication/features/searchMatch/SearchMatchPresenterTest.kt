package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch


import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.SearchMatchRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {

    @Mock
    private lateinit var view: SearchMatchView

    @Mock
    private lateinit var searchMatchRepository: SearchMatchRepository

    private lateinit var searchPresenter: SearchMatchPresenter

    private val query = "Arsenal"


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchMatchPresenter(view, searchMatchRepository)
    }


    @Test
    fun getSearchMatchDataSuccessTest() {

        val dataSearchMatch = mutableSetOf<SearchMatchItem>()
        val dataHomeBadge = mutableListOf<TeamDetailResponse>()
        val dataAwayBadge = mutableListOf<TeamDetailResponse>()

        val listSearchMatchResult = mutableListOf<SearchMatchItem>()
        val listHomeResult = mutableListOf<String>()
        val listAwayResult = mutableListOf<String>()
        val listStadiumResult = mutableListOf<String?>()

        searchPresenter.getSearchMatchData(query)

        argumentCaptor<RepositoryMultipleCallback<Set<SearchMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(searchMatchRepository).getSearchMatchData(eq(query), capture())
            firstValue.onDataLoaded(dataSearchMatch, dataHomeBadge, dataAwayBadge)
        }

        listSearchMatchResult.addAll(dataSearchMatch)

        dataHomeBadge.map {
            listHomeResult.add(it.teams[0].strTeamBadge)
            listStadiumResult.add(it.teams[0].strStadium)
        }

        dataAwayBadge.map {
            listAwayResult.add(it.teams[0].strTeamBadge)
        }

        verify(view).hideLoading()
        verify(view).loadSearchDataToView(listSearchMatchResult, listHomeResult, listAwayResult, listStadiumResult)
        verify(view).showLoading()
    }

    @Test
    fun getSearchMatchDataErrorTest() {
        searchPresenter.getSearchMatchData("")

        argumentCaptor<RepositoryMultipleCallback<Set<SearchMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(searchMatchRepository).getSearchMatchData(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}