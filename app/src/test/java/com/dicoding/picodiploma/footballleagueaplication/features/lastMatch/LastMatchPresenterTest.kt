package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch


import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.LastMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {

    @Mock
    private lateinit var view: LastMatchView

    @Mock
    private lateinit var lastMatchRepository: LastMatchRepository

    private lateinit var lastPresenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lastPresenter = LastMatchPresenter(view, lastMatchRepository)
    }

    @Test
    fun getLastMatchDataSuccessTest() {
        val idLeague = "4328"
        val dataMatch = mutableSetOf<LastMatchItem>()
        val dataHomeBadge = mutableListOf<TeamDetailResponse>()
        val dataAwayBadge = mutableListOf<TeamDetailResponse>()

        val listLastResult = mutableListOf<LastMatchItem>()
        val listHomeResult = mutableListOf<String>()
        val listAwayResult = mutableListOf<String>()
        val listStadiumResult = mutableListOf<String?>()
        val setDateResult = mutableSetOf<String>()

        lastPresenter.getLastMatchData(idLeague)

        argumentCaptor<RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(lastMatchRepository).getLastMatchData(eq(idLeague), capture())
            firstValue.onDataLoaded(dataMatch, dataHomeBadge, dataAwayBadge)
        }

        dataMatch.map {
            listLastResult.add(it)
            setDateResult.add(it.dateEvent)
        }

        dataHomeBadge.map {
            listHomeResult.add(it.teams[0].strTeamBadge)
            listStadiumResult.add(it.teams[0].strStadium)
        }

        dataAwayBadge.map {
            listAwayResult.add(it.teams[0].strTeamBadge)
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadLastMatchData(listLastResult, listHomeResult, listAwayResult, listStadiumResult, setDateResult)
    }

    @Test
    fun getLastMatchDataErrorTest() {
        lastPresenter.getLastMatchData("")


        argumentCaptor<RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(lastMatchRepository).getLastMatchData(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}