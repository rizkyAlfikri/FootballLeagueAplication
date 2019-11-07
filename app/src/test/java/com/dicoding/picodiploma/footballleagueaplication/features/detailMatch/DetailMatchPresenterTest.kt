package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch


import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.DetailMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {

    @Mock
    private lateinit var view: DetailMatchView

    @Mock
    private lateinit var detailMatchRepository: DetailMatchRepository

    @Mock
    private lateinit var matchDetailResponse: MatchDetailResponse

    @Mock
    private lateinit var homeTeamResponse: TeamDetailResponse

    @Mock
    private lateinit var awayTeamResponse: TeamDetailResponse

    private lateinit var detailMatchPresenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailMatchPresenter = DetailMatchPresenter(view, detailMatchRepository)
    }

    @Test
    fun getDetailMatchSuccessTest() {
        val idEvent = "441613"

        detailMatchPresenter.getDetailMatchData(idEvent)

        argumentCaptor<RepositoryCallback<MatchDetailResponse>>().apply {
            verify(detailMatchRepository).getDetailMatch(eq(idEvent), capture())
            firstValue.onDataLoaded(matchDetailResponse)
        }

        verify(view).hideLoading()
        verify(view).showLoading()
        verify(view).loadDataToView(matchDetailResponse.events)
    }

    @Test
    fun getDetailMatchErrorTest() {
        detailMatchPresenter.getDetailMatchData("")

        argumentCaptor<RepositoryCallback<MatchDetailResponse>>().apply {
            verify(detailMatchRepository).getDetailMatch(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).hideLoading()
        verify(view).showLoading()
        verify(view).onFailure("")
    }

    @Test
    fun getHomeTeamBadgeSuccessTest() {
        val idEvent = "441613"

        detailMatchPresenter.getDetailMatchData(idEvent)

        argumentCaptor<RepositoryCallback<TeamDetailResponse>>().apply {
            verify(detailMatchRepository).getHomeTeamBadge(eq(idEvent), capture())
            firstValue.onDataLoaded(homeTeamResponse)
        }

        homeTeamResponse.teams.map {
            verify(view).loadHomeBadgeToView(it.strTeamBadge)
        }

        verify(view).hideLoading()
        verify(view).showLoading()

    }

    @Test
    fun getHomeTeamBadgeErrorTest() {
        detailMatchPresenter.getDetailMatchData("")

        argumentCaptor<RepositoryCallback<TeamDetailResponse>>().apply {
            verify(detailMatchRepository).getHomeTeamBadge(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }

    @Test
    fun getAwayTeamBadgeSuccessTest() {
        val idEvent = "441613"

        detailMatchPresenter.getDetailMatchData(idEvent)

        argumentCaptor<RepositoryCallback<TeamDetailResponse>>().apply {
            verify(detailMatchRepository).getAwayTeamBadge(eq(idEvent), capture())
            firstValue.onDataLoaded(awayTeamResponse)
        }

        awayTeamResponse.teams.map {
            verify(view).loadAwayBadgeToView(it.strTeamBadge)
        }

        verify(view).showLoading()
        verify(view).hideLoading()
    }

    @Test
    fun getAwayTeamBadgeErrorTest() {
        detailMatchPresenter.getDetailMatchData("")

        argumentCaptor<RepositoryCallback<TeamDetailResponse>>().apply {
            verify(detailMatchRepository).getAwayTeamBadge(eq(""), capture())

            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}