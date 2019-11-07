package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class LeagueDetailRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var apiService: ApiService



    @Mock
    private lateinit var leagueDetailResponse: LeagueDetailResponse

    @Mock
    private lateinit var leagueRepositoryCallback: RepositoryCallback<LeagueDetailResponse>


    private lateinit var leagueDetailRepository: LeagueDetailRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        leagueDetailRepository = LeagueDetailRepository()
    }

    @Test
    fun getLeagueDetailSuccessTest() {

        Mockito.`when`(apiService.getLeagueDetailApi("4328"))
            .thenReturn(Observable.just(leagueDetailResponse))

        val actualLeagueDetail : Observable<LeagueDetailResponse> = apiService.getLeagueDetailApi("4328")
        val testObserver: TestObserver<LeagueDetailResponse> = actualLeagueDetail.test()
        testObserver.assertSubscribed()

        testObserver.assertResult(leagueDetailResponse)

        leagueDetailRepository.getLeagueDetail(
            "4328", leagueRepositoryCallback
        )

    }

    @Test
    fun getLeagueDetailErrorTest() {
        Mockito.`when`(apiService.getLeagueDetailApi(eq("")))
            .thenReturn(Observable.just(leagueDetailResponse))

        val actualLeagueDetail: Observable<LeagueDetailResponse> = apiService.getLeagueDetailApi(eq(""))
        val testObserver: TestObserver<LeagueDetailResponse> = actualLeagueDetail.test()
        testObserver.assertSubscribed()

        testObserver.assertResult(leagueDetailResponse)

        leagueDetailRepository.getLeagueDetail(eq(""), leagueRepositoryCallback)

        verify(leagueRepositoryCallback).onDataError("Failed request data to server")
    }
}