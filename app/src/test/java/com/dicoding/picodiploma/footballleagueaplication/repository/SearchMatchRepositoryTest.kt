package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiService
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

class SearchMatchRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var callbackSearch: RepositoryMultipleCallback<Set<SearchMatchItem>, List<TeamDetailResponse>>

    @Mock
    private lateinit var searchMatchResponse: SearchMatchResponse

    @Mock
    private lateinit var homeTeamResponse: TeamDetailResponse

    @Mock
    private lateinit var awayTeamResponse: TeamDetailResponse

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var searchMatchRepository: SearchMatchRepository

    private val idTeam = "133604"

    private val query = "Arsenal"

    private val idLeague = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchMatchRepository = SearchMatchRepository(idLeague)
    }

    @Test
    fun getSearchMatchSuccessTest() {
        Mockito.`when`(apiService.getSearchMatch(query))
            .thenReturn(Observable.just(searchMatchResponse))

        val actualSearch: Observable<SearchMatchResponse> =
                apiService.getSearchMatch(query)

        val testObserverSearch: TestObserver<SearchMatchResponse> = actualSearch.test()
        testObserverSearch.assertSubscribed()
        testObserverSearch.assertResult(searchMatchResponse)

        searchMatchRepository.getSearchMatchData(query, callbackSearch)
    }

    @Test
    fun getSearchMatchErrorTest() {
        Mockito.`when`(apiService.getSearchMatch(""))
            .thenReturn(Observable.just(searchMatchResponse))

        val actualSearch: Observable<SearchMatchResponse> =
            apiService.getSearchMatch("")

        val testObserverSearch: TestObserver<SearchMatchResponse> = actualSearch.test()
        testObserverSearch.assertSubscribed()
        testObserverSearch.assertResult(searchMatchResponse)

        searchMatchRepository.getSearchMatchData("", callbackSearch)

    }
    @Test
    fun getTeamHomeBadgeSuccessTest() {
        Mockito.`when`(apiService.getSearchMatch(query))
            .thenReturn(Observable.just(searchMatchResponse))

        val actualSearch: Observable<SearchMatchResponse> =
            apiService.getSearchMatch(query)

        val testObserverSearch: TestObserver<SearchMatchResponse> = actualSearch.test()
        testObserverSearch.assertSubscribed()
        testObserverSearch.assertResult(searchMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(homeTeamResponse))

        val actualHomeTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(homeTeamResponse)

        searchMatchRepository.getSearchMatchData(query, callbackSearch)
    }

    @Test
    fun getTeamHomeBadgeErrorTest() {
        Mockito.`when`(apiService.getSearchMatch(""))
            .thenReturn(Observable.just(searchMatchResponse))

        val actualSearch: Observable<SearchMatchResponse> =
            apiService.getSearchMatch("")

        val testObserverSearch: TestObserver<SearchMatchResponse> = actualSearch.test()
        testObserverSearch.assertSubscribed()
        testObserverSearch.assertResult(searchMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(homeTeamResponse))

        val actualHomeTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObserverHomeTeam: TestObserver<TeamDetailResponse> = actualHomeTeam.test()
        testObserverHomeTeam.assertSubscribed()
        testObserverHomeTeam.assertResult(homeTeamResponse)

        searchMatchRepository.getSearchMatchData("", callbackSearch)

    }

    @Test
    fun getTeamAwayBadgeSuccessTest() {
        Mockito.`when`(apiService.getSearchMatch(query))
            .thenReturn(Observable.just(searchMatchResponse))

        val actualSearch: Observable<SearchMatchResponse> =
            apiService.getSearchMatch(query)

        val testObserverSearch: TestObserver<SearchMatchResponse> = actualSearch.test()
        testObserverSearch.assertSubscribed()
        testObserverSearch.assertResult(searchMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(awayTeamResponse))

        val actualAwayTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer(idTeam)

        val testObserverAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObserverAwayTeam.assertSubscribed()
        testObserverAwayTeam.assertResult(awayTeamResponse)

        searchMatchRepository.getSearchMatchData(query, callbackSearch)
    }

    @Test
    fun getAwayTeamBadgeErrorTest() {
        Mockito.`when`(apiService.getSearchMatch(""))
            .thenReturn(Observable.just(searchMatchResponse))

        val actualSearch: Observable<SearchMatchResponse> =
            apiService.getSearchMatch("")

        val testObserverSearch: TestObserver<SearchMatchResponse> = actualSearch.test()
        testObserverSearch.assertSubscribed()
        testObserverSearch.assertResult(searchMatchResponse)

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(awayTeamResponse))

        val actualAwayTeam: Observable<TeamDetailResponse> =
                apiService.getTeamDetailFromServer("")

        val testObserverAwayTeam: TestObserver<TeamDetailResponse> = actualAwayTeam.test()
        testObserverAwayTeam.assertSubscribed()
        testObserverAwayTeam.assertResult(awayTeamResponse)

        searchMatchRepository.getSearchMatchData("", callbackSearch)

    }
}