package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class TeamOverviewRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var callbackTeamDetail: RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>

    @Mock
    private lateinit var callbackTable: RepositoryCallbackTable<List<Table>, List<TeamDetailItem>>

    @Mock
    private lateinit var teamDetailResponse: TeamDetailResponse

    @Mock
    private lateinit var teamTableResponse: TeamTableResponse

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var teamOverviewRepository: TeamOverviewRepository

    private val idTeam = "133604"


    private val idLeague = "4328"

    @Before
    fun setUpt() {
        MockitoAnnotations.initMocks(this)
        teamOverviewRepository = TeamOverviewRepository()
    }

    @Test
    fun getTeamOverviewSuccessTest() {
        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualTeamDetail: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverTeamDetail: TestObserver<TeamDetailResponse> = actualTeamDetail.test()
        testObserverTeamDetail.assertSubscribed()
        testObserverTeamDetail.assertResult(teamDetailResponse)

        teamOverviewRepository.getTeamOverview(idTeam, idLeague, callbackTable)
    }

    @Test
    fun getTeamOverviewErrorTest() {
        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualTeamDetail: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObserverTeamDetail: TestObserver<TeamDetailResponse> = actualTeamDetail.test()
        testObserverTeamDetail.assertSubscribed()
        testObserverTeamDetail.assertResult(teamDetailResponse)

        teamOverviewRepository.getTeamOverview("", "", callbackTeamDetail)

        verify(callbackTeamDetail).onDataError("Failed request team detail data to server")
    }

    @Test
    fun getTeamTableSuccessTest() {
        Mockito.`when`(apiService.getTableTeam(idLeague))
            .thenReturn(Observable.just(teamTableResponse))

        val actualTeamTable: Observable<TeamTableResponse> =
                apiService.getTableTeam(idLeague)

        val testObserverTeamTable: TestObserver<TeamTableResponse> = actualTeamTable.test()
        testObserverTeamTable.assertSubscribed()
        testObserverTeamTable.assertResult(teamTableResponse)

        teamOverviewRepository.getTeamOverview(idTeam, idLeague,  callbackTable)
    }

    @Test
    fun getTeamTableErrorTest() {
        Mockito.`when`(apiService.getTableTeam(""))
            .thenReturn(Observable.just(teamTableResponse))

        val actualTeamTable: Observable<TeamTableResponse> =
                apiService.getTableTeam("")

        val testObserverTeamTable: TestObserver<TeamTableResponse> = actualTeamTable.test()
        testObserverTeamTable.assertSubscribed()
        testObserverTeamTable.assertResult(teamTableResponse)

        teamOverviewRepository.getTeamOverview("", "", callbackTable)

        verify(callbackTable).onDataError("Failed request team detail data to server")
    }
}