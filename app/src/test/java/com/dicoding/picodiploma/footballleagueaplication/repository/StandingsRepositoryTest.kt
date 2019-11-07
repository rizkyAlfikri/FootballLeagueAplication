package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
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

class StandingsRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testObserver = RxImmediateSchedulerRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var callback: RepositoryCallbackTable<List<Table>, List<String>>

    @Mock
    private lateinit var teamTableResponse: TeamTableResponse

    @Mock
    private lateinit var teamDetailResponse: TeamDetailResponse

    private lateinit var standingsRepository: StandingsRepository

    private val idTeam = "133604"

    private val idLeague = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        standingsRepository = StandingsRepository()
    }

    @Test
    fun getStandingTableSuccessTest() {
        Mockito.`when`(apiService.getTableTeam(idLeague))
            .thenReturn(Observable.just(teamTableResponse))

        val actualObserverStanding: Observable<TeamTableResponse> =
            apiService.getTableTeam(idLeague)

        val testObserverStanding: TestObserver<TeamTableResponse> = actualObserverStanding.test()
        testObserverStanding.assertSubscribed()
        testObserverStanding.assertResult(teamTableResponse)

        standingsRepository.getStandingTable(idLeague, callback)
    }

    @Test
    fun getStandingTableErrorTest() {
        Mockito.`when`(apiService.getTableTeam(""))
            .thenReturn(Observable.just(teamTableResponse))

        val actualObserverStanding: Observable<TeamTableResponse> =
            apiService.getTableTeam("")

        val testObserverStanding: TestObserver<TeamTableResponse> = actualObserverStanding.test()
        testObserverStanding.assertSubscribed()
        testObserverStanding.assertResult(teamTableResponse)

        standingsRepository.getStandingTable("", callback)
    }

    @Test
    fun getStandingTeamSuccessTest() {
        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualObserverStandingTeam: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer(idTeam)

        val testObserverStandingTeam: TestObserver<TeamDetailResponse> =
            actualObserverStandingTeam.test()
        testObserverStandingTeam.assertSubscribed()
        testObserverStandingTeam.assertResult(teamDetailResponse)

        standingsRepository.getStandingTable(idLeague, callback)
    }

    @Test
    fun getStandingTeamErrorTest() {
        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualObserverStandingTeam: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer("")

        val testObserverStandingTeam: TestObserver<TeamDetailResponse> =
            actualObserverStandingTeam.test()
        testObserverStandingTeam.assertSubscribed()
        testObserverStandingTeam.assertResult(teamDetailResponse)

        standingsRepository.getStandingTable("", callback)
    }
}