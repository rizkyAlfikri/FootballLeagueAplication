package com.dicoding.picodiploma.footballleagueaplication.features.home

import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.LeagueDetailRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

import org.junit.Before
import org.mockito.*

class LeagueDetailPresenterTest {

    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var leagueDetailRepository: LeagueDetailRepository

    @Mock
    private lateinit var leagueDetailResponse: LeagueDetailResponse

    private lateinit var detailPresenter: LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailPresenter = LeagueDetailPresenter(view, leagueDetailRepository)
    }

    @Test
    fun getLeagueDetailSuccessTest() {
        val idLeague = "4328"
        var listBanner = mutableListOf<String>()
        detailPresenter.getLeagueDetail(idLeague)

        argumentCaptor<RepositoryCallback<LeagueDetailResponse>>().apply {
            verify(leagueDetailRepository).getLeagueDetail(eq(idLeague), capture())
            firstValue.onDataLoaded(leagueDetailResponse)
            leagueDetailResponse.leagues.map { listBanner = mutableListOf(
                it.strBadge,
                it.strPoster,
                it.strFanart1,
                it.strFanart2,
                it.strFanart3,
                it.strFanart4,
                it.strTrophy) }
            }


        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).loadDataToView(leagueDetailResponse)
        verify(view).loadBannerToView(listBanner)

    }

    @Test
    fun getLeagueDetailErrorTest() {
        detailPresenter.getLeagueDetail("")

        argumentCaptor<RepositoryCallback<LeagueDetailResponse>>().apply {
            verify(leagueDetailRepository).getLeagueDetail(eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).hideLoading()
        verify(view).showLoading()
        verify(view).onFailure("")
    }
}
