package com.dicoding.picodiploma.footballleagueaplication.features.home

import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test


import org.junit.Before
import org.mockito.*

class LeagueDetailPresenterTest {

    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>


    private lateinit var detailPresenter: LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailPresenter = LeagueDetailPresenter(view, gson, apiRepository, TestContextProvider())


    }

    @Test
    fun getLeagueDetail() {
        val listLeague = mutableListOf<LeagueDetailItem>()
        val response = LeagueDetailResponse(listLeague)
        val league = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueDetailResponse::class.java
                )
            ).thenReturn(response)



            detailPresenter.getLeagueDetail(league)
            Mockito.verify(view).showLoading()
//            Mockito.verify(view).loadBannerToView(listBanner)
            Mockito.verify(view).loadDataToView(response)
//            Mockito.verify(view).onFailure(throwable)
            Mockito.verify(view).hideLoading()
        }
    }
}
