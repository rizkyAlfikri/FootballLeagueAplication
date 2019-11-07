package com.dicoding.picodiploma.footballleagueaplication.features.playerdetail

import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.PlayerDetailRepository
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PlayerDetailPresenterTest {

    @Mock
    private lateinit var view: PlayerDetailView

    @Mock
    private lateinit var playerRepository: PlayerDetailRepository

    @Mock
    private lateinit var dataPlayer: PlayerDetailResponse

    @Mock
    private lateinit var dataHonors: PlayerHonorsResponse

    private lateinit var playerDetailPresenter: PlayerDetailPresenter

    private val idPlayer = "34145444"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerDetailPresenter = PlayerDetailPresenter(view, playerRepository)
    }

    @Test
    fun getPlayerDetailDataSuccessTest() {

        playerDetailPresenter.getPlayerDetail(idPlayer)

        argumentCaptor<RepositoryCallback<PlayerDetailResponse>>().apply {
            verify(playerRepository).getPlayerDetail(eq(idPlayer), capture())
            firstValue.onDataLoaded(dataPlayer)
        }

        view.showLoading()
        view.hideLoading()
        view.loadPlayerToView(dataPlayer.players)
    }

    @Test
    fun getPlayerDetailDataErrorTest() {

        playerDetailPresenter.getPlayerDetail("")

        argumentCaptor<RepositoryCallback<PlayerDetailResponse>>().apply {
            verify(playerRepository).getPlayerDetail(eq(""), capture())
            firstValue.onDataLoaded(dataPlayer)
        }

        view.showLoading()
        view.hideLoading()
        view.onFailure("")
    }

    @Test
    fun getPlayerHonorDataSuccessTest() {

        playerDetailPresenter.getPlayerDetail(idPlayer)

        argumentCaptor<RepositoryCallback<PlayerHonorsResponse>>().apply {
            verify(playerRepository).getPlayerHonour(eq(idPlayer), capture())
            firstValue.onDataLoaded(dataHonors)
        }

        view.showLoading()
        view.hideLoading()
        view.loadPlayerHonorToView(dataHonors.honors)
    }

    @Test
    fun getPlayerHonorDataErrorTest() {

        playerDetailPresenter.getPlayerDetail("")

        argumentCaptor<RepositoryCallback<PlayerHonorsResponse>>().apply {
            verify(playerRepository).getPlayerHonour(eq(""), capture())
            firstValue.onDataError("")
        }

        view.showLoading()
        view.hideLoading()
        view.onFailure("")
    }

}