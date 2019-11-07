package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
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

class NextMatchRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerTest = RxImmediateSchedulerRule()

    @Mock
    private lateinit var callbackNextMatch: RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>

    @Mock
    private lateinit var nextMatchResponse: NextMatchResponse

    @Mock
    private lateinit var homeTeamResponse: TeamDetailResponse

    @Mock
    private lateinit var awayTeamResponse: TeamDetailResponse

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var nextMatchRepository: NextMatchRepository

    private val idLeague = "4328"

    private val idTeam = "133604"


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        nextMatchRepository = NextMatchRepository()
    }

    @Test
    fun getNextMatchSuccessTest() {
        Mockito.`when`(apiService.getNextMatch(idLeague))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualNextMatch:Observable<NextMatchResponse> =
                apiService.getNextMatch(idLeague)

        val testObserverNextMatch: TestObserver<NextMatchResponse> = actualNextMatch.test()
        testObserverNextMatch.assertSubscribed()
        testObserverNextMatch.assertResult(nextMatchResponse)

        nextMatchRepository.getNextMatchData(idLeague, callbackNextMatch)
    }

    @Test
    fun getNextMatchErrorTest() {
        Mockito.`when`(apiService.getNextMatch(""))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualNextMatch: Observable<NextMatchResponse> =
                apiService.getNextMatch("")

        val testObserverNextMatch: TestObserver<NextMatchResponse> = actualNextMatch.test()
        testObserverNextMatch.assertSubscribed()
        testObserverNextMatch.assertResult(nextMatchResponse)

        nextMatchRepository.getNextMatchData("", callbackNextMatch)

        verify(callbackNextMatch).onDataError("Failed request next match data to server")
    }

    @Test
    fun getHomeTeamBadgeSuccessTest() {
        Mockito.`when`(apiService.getNextMatch(idLeague))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualNextMatch:Observable<NextMatchResponse> =
            apiService.getNextMatch(idLeague)

        val testObserverNextMatch: TestObserver<NextMatchResponse> = actualNextMatch.test()
        testObserverNextMatch.assertSubscribed()
        testObserverNextMatch.assertResult(nextMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(homeTeamResponse))

        val actualHomeTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(homeTeamResponse)

        nextMatchRepository.getNextMatchData(idLeague, callbackNextMatch)
    }

    @Test
    fun getHomeTeamBadgeErrorTest() {
        Mockito.`when`(apiService.getNextMatch(""))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualNextMatch: Observable<NextMatchResponse> =
            apiService.getNextMatch("")

        val testObserverNextMatch: TestObserver<NextMatchResponse> = actualNextMatch.test()
        testObserverNextMatch.assertSubscribed()
        testObserverNextMatch.assertResult(nextMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(homeTeamResponse))

        val actualHomeTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(homeTeamResponse)

        nextMatchRepository.getNextMatchData("", callbackNextMatch)

        verify(callbackNextMatch).onDataError("Failed request next match data to server")
    }

    @Test
    fun getAwayTeamBadgeSuccessTest() {
        Mockito.`when`(apiService.getNextMatch(idLeague))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualNextMatch:Observable<NextMatchResponse> =
            apiService.getNextMatch(idLeague)

        val testObserverNextMatch: TestObserver<NextMatchResponse> = actualNextMatch.test()
        testObserverNextMatch.assertSubscribed()
        testObserverNextMatch.assertResult(nextMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(awayTeamResponse))

        val actualAwayTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObserverAwayTeam.assertSubscribed()
        testObserverAwayTeam.assertResult(awayTeamResponse)

        nextMatchRepository.getNextMatchData(idLeague, callbackNextMatch)
    }

    @Test
    fun getAwayTeamBadgeErrorTest() {
        Mockito.`when`(apiService.getNextMatch(""))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualNextMatch: Observable<NextMatchResponse> =
            apiService.getNextMatch("")

        val testObserverNextMatch: TestObserver<NextMatchResponse> = actualNextMatch.test()
        testObserverNextMatch.assertSubscribed()
        testObserverNextMatch.assertResult(nextMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(awayTeamResponse))

        val actualAwayTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObserverAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObserverAwayTeam.assertSubscribed()
        testObserverAwayTeam.assertResult(awayTeamResponse)

        nextMatchRepository.getNextMatchData("", callbackNextMatch)

        verify(callbackNextMatch).onDataError("Failed request next match data to server")
    }
}