package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamItem
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamResponse
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

class SearchTeamRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testObserver = RxImmediateSchedulerRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var searchTeamResponse: SearchTeamResponse

    @Mock
    private lateinit var teamTableResponse: TeamTableResponse

    @Mock
    private lateinit var callbackTable: RepositoryCallbackTable<Set<Table>, Set<SearchTeamItem>>

    private lateinit var searchTeamRepository: SearchTeamRepository

    private val idLeague = "4328"

    private val query = "Arsenal"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchTeamRepository = SearchTeamRepository()
    }

    @Test
    fun getSearchTeamDataSuccessTest() {

        Mockito.`when`(apiService.getSearchTeamApi(query))
            .thenReturn(Observable.just(searchTeamResponse))

        val actualObserverSearchTeam: Observable<SearchTeamResponse> =
            apiService.getSearchTeamApi(query)

        val testObservableSearchTeam: TestObserver<SearchTeamResponse> =
            actualObserverSearchTeam.test()
        testObservableSearchTeam.assertSubscribed()
        testObservableSearchTeam.assertResult(searchTeamResponse)

        searchTeamRepository.getSearchTeamData(query, idLeague, callbackTable)
    }

    @Test
    fun getSearchTeamDataErrorTest() {

        Mockito.`when`(apiService.getSearchTeamApi(""))
            .thenReturn(Observable.just(searchTeamResponse))

        val actualObserverSearchTeam: Observable<SearchTeamResponse> =
            apiService.getSearchTeamApi("")

        val testObservableSearchTeam: TestObserver<SearchTeamResponse> =
            actualObserverSearchTeam.test()
        testObservableSearchTeam.assertSubscribed()
        testObservableSearchTeam.assertResult(searchTeamResponse)

        searchTeamRepository.getSearchTeamData("", "", callbackTable)
    }

    @Test
    fun getTableTeamDataSuccessTest() {
        Mockito.`when`(apiService.getTableTeam(idLeague))
            .thenReturn(Observable.just(teamTableResponse))

        val actualObserverTeamTable: Observable<TeamTableResponse> =
            apiService.getTableTeam(idLeague)

        val testObserverTeamTable: TestObserver<TeamTableResponse> = actualObserverTeamTable.test()
        testObserverTeamTable.assertSubscribed()
        testObserverTeamTable.assertResult(teamTableResponse)

        searchTeamRepository.getSearchTeamData(query, idLeague, callbackTable)
    }

    @Test
    fun getTableTeamDataErrorTest() {
        Mockito.`when`(apiService.getTableTeam(""))
            .thenReturn(Observable.just(teamTableResponse))

        val actualObserverTeamTable: Observable<TeamTableResponse> =
            apiService.getTableTeam("")

        val testObserverTeamTable: TestObserver<TeamTableResponse> = actualObserverTeamTable.test()
        testObserverTeamTable.assertSubscribed()
        testObserverTeamTable.assertResult(teamTableResponse)

        searchTeamRepository.getSearchTeamData("", "", callbackTable)
    }

}