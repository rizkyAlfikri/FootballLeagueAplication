package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
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

class TeamRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var callbackTeam: RepositoryCallback<TeamResponse>

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var teamResponse: TeamResponse

    private lateinit var teamRepository: TeamRepository

    private val idLeague = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamRepository = TeamRepository()
    }

    @Test
    fun getTeamListSuccessTest() {
        Mockito.`when`(apiService.getTeamFromServer(idLeague))
            .thenReturn(Observable.just(teamResponse))

        val actualTeam: Observable<TeamResponse> =
            apiService.getTeamFromServer(idLeague)

        val testObserverTeam: TestObserver<TeamResponse> = actualTeam.test()
        testObserverTeam.assertSubscribed()
        testObserverTeam.assertResult(teamResponse)

        teamRepository.getTeamList(idLeague, callbackTeam)
    }

    @Test
    fun getTeamListErrorTest() {
        Mockito.`when`(apiService.getTeamFromServer(""))
            .thenReturn(Observable.just(teamResponse))

        val actualTeam: Observable<TeamResponse> =
                apiService.getTeamFromServer("")

        val testObserverTeam: TestObserver<TeamResponse> = actualTeam.test()
        testObserverTeam.assertSubscribed()
        testObserverTeam.assertResult(teamResponse)

        teamRepository.getTeamList("", callbackTeam)

        verify(callbackTeam).onDataError("Failed request team data to server")
    }
}