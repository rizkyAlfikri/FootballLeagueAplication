package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
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

class TeamPlayerRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var callbackPlayer: RepositoryCallback<PlayerResponse>

    @Mock
    private lateinit var playerResponse: PlayerResponse

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var teamPlayerRepository: TeamPlayerRepository

    private val idTeam = "133604"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamPlayerRepository = TeamPlayerRepository()
    }

    @Test
    fun getTeamPlayerSuccessTest() {
        Mockito.`when`(apiService.getPlayer(idTeam))
            .thenReturn(Observable.just(playerResponse))

        val actualPlayer: Observable<PlayerResponse> =
                apiService.getPlayer(idTeam)

        val testObserverPlayer: TestObserver<PlayerResponse> = actualPlayer.test()
        testObserverPlayer.assertSubscribed()
        testObserverPlayer.assertResult(playerResponse)

        teamPlayerRepository.getTeamPlayer(idTeam, callbackPlayer)
    }

    @Test
    fun getTeamPlayerErrorTest() {
        Mockito.`when`(apiService.getPlayer(""))
            .thenReturn(Observable.just(playerResponse))

        val actualPlayer: Observable<PlayerResponse> =
                apiService.getPlayer("")

        val testObserverPlayer: TestObserver<PlayerResponse> = actualPlayer.test()
        testObserverPlayer.assertSubscribed()
        testObserverPlayer.assertResult(playerResponse)

        teamPlayerRepository.getTeamPlayer("", callbackPlayer)

        verify(callbackPlayer).onDataError("Failed request player data to server")
    }
}