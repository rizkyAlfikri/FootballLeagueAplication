package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
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

class DetailMatchRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var callbackDetailMatch: RepositoryCallback<MatchDetailResponse>

    @Mock
    private lateinit var callbackTeamBadge: RepositoryCallback<TeamDetailResponse>

    @Mock
    private lateinit var detailResponse: MatchDetailResponse

    @Mock
    private lateinit var homeTeamDetailResponse: TeamDetailResponse

    @Mock
    private lateinit var awayTeamDetailResponse: TeamDetailResponse

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var detailMatchRepository: DetailMatchRepository

    private val idEvent = "441613"

    private val idTeam = "133604"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailMatchRepository = DetailMatchRepository()
    }

    @Test
    fun getDetailMatchSuccessTest() {

        Mockito.`when`(apiService.getMatchDetailApi(idEvent))
            .thenReturn(Observable.just(detailResponse))

        val actualDetailMatch: Observable<MatchDetailResponse> = apiService.getMatchDetailApi(idEvent)
        val testObserver: TestObserver<MatchDetailResponse> = actualDetailMatch.test()
        testObserver.assertSubscribed()
        testObserver.assertResult(detailResponse)

        detailMatchRepository.getDetailMatch(idEvent, callbackDetailMatch)

    }

    @Test
    fun getDetailMatchErrorTest() {

        Mockito.`when`(apiService.getMatchDetailApi(""))
            .thenReturn(Observable.just(detailResponse))

        val actualDetailMatch: Observable<MatchDetailResponse> = apiService.getMatchDetailApi("")
        val testObserver: TestObserver<MatchDetailResponse> = actualDetailMatch.test()
        testObserver.assertSubscribed()
        testObserver.assertResult(detailResponse)

        detailMatchRepository.getDetailMatch("", callbackDetailMatch)

        verify(callbackDetailMatch).onDataError("Failed request detail match data to server")
    }




    @Test
    fun getHomeTeamBadgeSuccessTest() {

        Mockito.`when`(apiService.getMatchDetailApi(idEvent))
            .thenReturn(Observable.just(detailResponse))

        val actualDetailMatch: Observable<MatchDetailResponse> =
            apiService.getMatchDetailApi(idEvent)

        val testObserverDetailMatch: TestObserver<MatchDetailResponse> = actualDetailMatch.test()
        testObserverDetailMatch.assertSubscribed()
        testObserverDetailMatch.assertResult(detailResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(homeTeamDetailResponse))

        val actualHomeTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(homeTeamDetailResponse)

        detailMatchRepository.getHomeTeamBadge(idEvent, callbackTeamBadge)
    }

    @Test
    fun getHomeTeamBadgeErrorTest() {

        Mockito.`when`(apiService.getMatchDetailApi(""))
            .thenReturn(Observable.just(detailResponse))

        val actualDetailMatch: Observable<MatchDetailResponse> =
                apiService.getMatchDetailApi("")

        val testObserverDetailMatch: TestObserver<MatchDetailResponse> = actualDetailMatch.test()
        testObserverDetailMatch.assertSubscribed()
        testObserverDetailMatch.assertResult(detailResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(homeTeamDetailResponse))

        val actualHomeTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(homeTeamDetailResponse)

        detailMatchRepository.getHomeTeamBadge("", callbackTeamBadge)

        verify(callbackTeamBadge).onDataError("Failed request team home badge to server")
    }

    @Test
    fun getAwayTeamBadgeSuccessTest() {

        Mockito.`when`(apiService.getMatchDetailApi(idEvent))
            .thenReturn(Observable.just(detailResponse))

        val actualDetailResponse: Observable<MatchDetailResponse> =
                apiService.getMatchDetailApi(idEvent)

        val testObserverDetailMatch: TestObserver<MatchDetailResponse> = actualDetailResponse.test()
        testObserverDetailMatch.assertSubscribed()
        testObserverDetailMatch.assertResult(detailResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(awayTeamDetailResponse))

        val actualAwayTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObserverAwayTeam.assertSubscribed()
        testObserverAwayTeam.assertResult(awayTeamDetailResponse)

        detailMatchRepository.getAwayTeamBadge(idEvent, callbackTeamBadge)
    }

    @Test
    fun getAwayTeamBadgeErrorTest() {

        Mockito.`when`(apiService.getMatchDetailApi(""))
            .thenReturn(Observable.just(detailResponse))

        val actualDetailResponse: Observable<MatchDetailResponse> =
            apiService.getMatchDetailApi("")

        val testObserverDetailMatch: TestObserver<MatchDetailResponse> = actualDetailResponse.test()
        testObserverDetailMatch.assertSubscribed()
        testObserverDetailMatch.assertResult(detailResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(awayTeamDetailResponse))

        val actualAwayTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObservableAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObservableAwayTeam.assertSubscribed()
        testObservableAwayTeam.assertResult(awayTeamDetailResponse)

        detailMatchRepository.getAwayTeamBadge("", callbackTeamBadge)

        verify(callbackTeamBadge).onDataError("Failed request team away badge data to server")
    }
}