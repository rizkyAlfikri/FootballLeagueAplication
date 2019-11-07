package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsResponse
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

class PlayerDetailRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testObserver = RxImmediateSchedulerRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var playerHonorsResponse: PlayerHonorsResponse

    @Mock
    private lateinit var playerDetailResponse: PlayerDetailResponse

    @Mock
    private lateinit var callbackPlayer: RepositoryCallback<PlayerDetailResponse>

    @Mock
    private lateinit var callbackHonors: RepositoryCallback<PlayerHonorsResponse>

    private lateinit var playerDetailRepository: PlayerDetailRepository

    private val idPlayer = "34145444"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerDetailRepository = PlayerDetailRepository()
    }

    @Test
    fun getPlayerDetailSuccessTest() {

        Mockito.`when`(apiService.getPlayerDetailApi(idPlayer))
            .thenReturn(Observable.just(playerDetailResponse))

        val actualObserverPlayerDetail: Observable<PlayerDetailResponse> =
            apiService.getPlayerDetailApi(idPlayer)

        val testObserverPlayerDetail: TestObserver<PlayerDetailResponse> =
            actualObserverPlayerDetail.test()
        testObserverPlayerDetail.assertSubscribed()
        testObserverPlayerDetail.assertResult(playerDetailResponse)

        playerDetailRepository.getPlayerDetail(idPlayer, callbackPlayer)
    }

    @Test
    fun getPlayerDetailErrorTest() {

        Mockito.`when`(apiService.getPlayerDetailApi(""))
            .thenReturn(Observable.just(playerDetailResponse))

        val actualObserverPlayerDetail: Observable<PlayerDetailResponse> =
            apiService.getPlayerDetailApi("")

        val testObserverPlayerDetail: TestObserver<PlayerDetailResponse> =
            actualObserverPlayerDetail.test()
        testObserverPlayerDetail.assertSubscribed()
        testObserverPlayerDetail.assertResult(playerDetailResponse)

        playerDetailRepository.getPlayerDetail("", callbackPlayer)
    }

    @Test
    fun getPlayerHonorsSuccessTest() {

        Mockito.`when`(apiService.getPlayerHonorsApi(idPlayer))
            .thenReturn(Observable.just(playerHonorsResponse))

        val actualObserverPlayerHonor: Observable<PlayerHonorsResponse> =
            apiService.getPlayerHonorsApi(idPlayer)

        val testObserverPlayerDetail: TestObserver<PlayerHonorsResponse> =
            actualObserverPlayerHonor.test()
        testObserverPlayerDetail.assertSubscribed()
        testObserverPlayerDetail.assertResult(playerHonorsResponse)

        playerDetailRepository.getPlayerHonour(idPlayer, callbackHonors)
    }

    @Test
    fun getPlayerHonorsErrorTest() {

        Mockito.`when`(apiService.getPlayerHonorsApi(""))
            .thenReturn(Observable.just(playerHonorsResponse))

        val actualObserverPlayerHonor: Observable<PlayerHonorsResponse> =
            apiService.getPlayerHonorsApi("")

        val testObserverPlayerDetail: TestObserver<PlayerHonorsResponse> =
            actualObserverPlayerHonor.test()
        testObserverPlayerDetail.assertSubscribed()
        testObserverPlayerDetail.assertResult(playerHonorsResponse)

        playerDetailRepository.getPlayerHonour("", callbackHonors)
    }
}