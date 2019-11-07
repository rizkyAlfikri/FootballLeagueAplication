package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
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

class LastMatchRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var callbackLastMatch: RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>

    @Mock
    private lateinit var lastMatchResponse: LastMatchResponse

    @Mock
    private lateinit var dataHomeBadge: TeamDetailResponse

    @Mock
    private lateinit var dataAwayBadge: TeamDetailResponse

    private lateinit var lastMatchRepository: LastMatchRepository

    private val idLeague = "4328"

    private val idTeam = "133604"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lastMatchRepository = LastMatchRepository()
    }

    @Test
    fun getLastMatchSuccessTest() {

        Mockito.`when`(apiService.getLastMatch(idLeague))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualLastMatch: Observable<LastMatchResponse> =
            apiService.getLastMatch(idLeague)

        val testObserverLastMatch: TestObserver<LastMatchResponse> = actualLastMatch.test()
        testObserverLastMatch.assertSubscribed()
        testObserverLastMatch.assertResult(lastMatchResponse)

        lastMatchRepository.getLastMatchData(idLeague, callbackLastMatch)
    }

    @Test
    fun getLastMatchErrorTest() {
        Mockito.`when`(apiService.getLastMatch(""))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualLastMatch: Observable<LastMatchResponse> =
            apiService.getLastMatch("")

        val testObserverLastMatch: TestObserver<LastMatchResponse> = actualLastMatch.test()
        testObserverLastMatch.assertSubscribed()
        testObserverLastMatch.assertResult(lastMatchResponse)

        lastMatchRepository.getLastMatchData("", callbackLastMatch)

        verify(callbackLastMatch).onDataError("Failed request last match data to server")

    }

    @Test
    fun getHomeTeamBadge() {
        Mockito.`when`(apiService.getLastMatch(idLeague))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualLastMatch: Observable<LastMatchResponse> =
            apiService.getLastMatch(idLeague)

        val testObserverLastMatch: TestObserver<LastMatchResponse> = actualLastMatch.test()
        testObserverLastMatch.assertSubscribed()
        testObserverLastMatch.assertResult(lastMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(dataHomeBadge))

        val actualHomeTeam: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer(idTeam)

        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(dataHomeBadge)

        lastMatchRepository.getLastMatchData(idLeague, callbackLastMatch)

    }

    @Test
    fun getHomeTeamBadgeErrorTest() {
        Mockito.`when`(apiService.getLastMatch(""))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualLastMatch: Observable<LastMatchResponse> =
            apiService.getLastMatch("")

        val testObserverLastMatch: TestObserver<LastMatchResponse> = actualLastMatch.test()
        testObserverLastMatch.assertSubscribed()
        testObserverLastMatch.assertResult(lastMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(dataHomeBadge))

        val actualHomeTeam: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer("")
        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(dataHomeBadge)

        lastMatchRepository.getLastMatchData("", callbackLastMatch)

//        verify(callbackTeamDetail).onDataError("Failed request last match data to server")
    }

    @Test
    fun getAwayTeamBadgeSuccessTest() {
        Mockito.`when`(apiService.getLastMatch(idLeague))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualLastMatch: Observable<LastMatchResponse> =
            apiService.getLastMatch(idLeague)

        val testObservableLastMatch: TestObserver<LastMatchResponse> = actualLastMatch.test()
        testObservableLastMatch.assertSubscribed()
        testObservableLastMatch.assertResult(lastMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(dataAwayBadge))

        val actualAwayTeam: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer(idTeam)

        val testObservableAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObservableAwayTeam.assertSubscribed()
        testObservableAwayTeam.assertResult(dataAwayBadge)

        lastMatchRepository.getLastMatchData(idLeague, callbackLastMatch)
    }

    @Test
    fun getAwayTeamBadgeErrorTest() {
        Mockito.`when`(apiService.getLastMatch(""))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualLastMatch: Observable<LastMatchResponse> =
            apiService.getLastMatch("")

        val testObservableLastMatch: TestObserver<LastMatchResponse> = actualLastMatch.test()
        testObservableLastMatch.assertSubscribed()
        testObservableLastMatch.assertResult(lastMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(dataAwayBadge))

        val actualAwayTeam: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer("")

        val testObservableAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObservableAwayTeam.assertSubscribed()
        testObservableAwayTeam.assertResult(dataAwayBadge)

        lastMatchRepository.getLastMatchData("", callbackLastMatch)

//        verify(callbackTeamDetail).onDataError("Failed request last match data to server")
    }
}