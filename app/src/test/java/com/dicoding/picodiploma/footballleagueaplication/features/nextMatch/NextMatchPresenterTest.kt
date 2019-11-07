package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch


import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.NextMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    private lateinit var view: NextMatchView

    @Mock
    private lateinit var nextMatchRepository: NextMatchRepository

    private lateinit var nextPresenter: NextMatchPresenter

    private val idLeague = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        nextPresenter = NextMatchPresenter(view, nextMatchRepository)

    }

    @Test
    fun getNextMatchDataSuccessTest() {
        val dataNextMatch = mutableSetOf<NextMatchItem>()
        val dataHomeBadge = mutableListOf<TeamDetailResponse>()
        val dataAwayBadge = mutableListOf<TeamDetailResponse>()

        val listNextMatchResult = mutableListOf<NextMatchItem>()
        val listHomeResult = mutableListOf<String>()
        val listAwayResult = mutableListOf<String>()
        val listStadiumResult = mutableListOf<String?>()
        val setDateResult = mutableSetOf<String>()

        nextPresenter.getNextMatchData(idLeague)

        argumentCaptor<RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(nextMatchRepository).getNextMatchData(eq(idLeague), capture())
            firstValue.onDataLoaded(dataNextMatch, dataHomeBadge, dataAwayBadge)
        }

        dataNextMatch.map {
            listNextMatchResult.add(it)
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
        verify(view).loadNextMatchData(
            listNextMatchResult,
            listHomeResult,
            listAwayResult,
            listStadiumResult,
            setDateResult
        )
    }

    @Test
    fun getNextMatchDataErrorTest() {
        nextPresenter.getNextMatchData("")

        argumentCaptor<RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(nextMatchRepository).getNextMatchData(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}