package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
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

class TeamDetailRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var callbackTeamDetail: RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>

    @Mock
    private lateinit var teamDetailResponse: TeamDetailResponse

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var teamDetailRepository: TeamDetailRepository

    private val idTeam = "133604"

    private val idLeague = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamDetailRepository = TeamDetailRepository()
    }

    @Test
    fun getTeamDetailSuccessTest() {

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualTeamDetail: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverTeamDetail: TestObserver<TeamDetailResponse> = actualTeamDetail.test()
        testObserverTeamDetail.assertSubscribed()
        testObserverTeamDetail.assertResult(teamDetailResponse)

        teamDetailRepository.getTeamDetail(idTeam, idLeague, callbackTeamDetail)
    }

    @Test
    fun getTeamDetailErrorTest() {

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualTeamDetail: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObserverTeamDetail: TestObserver<TeamDetailResponse> = actualTeamDetail.test()
        testObserverTeamDetail.assertSubscribed()
        testObserverTeamDetail.assertResult(teamDetailResponse)

        teamDetailRepository.getTeamDetail("", "", callbackTeamDetail)

        verify(callbackTeamDetail).onDataError("Failed request team detail data to server")
    }
}