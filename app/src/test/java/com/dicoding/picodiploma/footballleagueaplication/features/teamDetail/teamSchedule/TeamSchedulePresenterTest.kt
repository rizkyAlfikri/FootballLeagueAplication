package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryMultipleCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamScheduleRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test


import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TeamSchedulePresenterTest {

    @Mock
    private lateinit var view: TeamScheduleView

    @Mock
    private lateinit var scheduleRepository: TeamScheduleRepository

    private lateinit var schedulePresenter: TeamSchedulePresenter
    private lateinit var idLeague: String
    private lateinit var idTeam: String

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulePresenter = TeamSchedulePresenter(view, scheduleRepository)
        idLeague = "4328"
        idTeam = "133604"
    }

    @Test
    fun getLastMatchDataSuccessTest() {
        val dataLastMatch = mutableSetOf<LastMatchItem>()
        val dataHomeBadge = mutableListOf<TeamDetailResponse>()
        val dataAwayBadge = mutableListOf<TeamDetailResponse>()


        val listLastSchedule = mutableListOf<LastMatchItem>()
        val listHomeBadge = mutableListOf<String>()
        val listAwayBadge = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()

        schedulePresenter.getLastSchedule(idLeague, idTeam)

        argumentCaptor<RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(scheduleRepository).getLastScheduleData(eq(idLeague), eq(idTeam), capture())
            firstValue.onDataLoaded(dataLastMatch, dataHomeBadge, dataAwayBadge)
        }

        listLastSchedule.addAll(dataLastMatch)

        dataHomeBadge.map {
            listHomeBadge.add(it.teams[0].strTeamBadge)
            listStadium.add(it.teams[0].strStadium)
        }

        dataAwayBadge.map {
            listAwayBadge.add(it.teams[0].strTeamBadge)
        }

        verify(view).showLoading()
        verify(view).loadLastSchedule(listLastSchedule, listHomeBadge, listAwayBadge, listStadium)
    }


    @Test
    fun getLastMatchDataErrorTest() {

        schedulePresenter.getLastSchedule("", "")

        argumentCaptor<RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(scheduleRepository).getLastScheduleData(eq(""), eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).onFailure("")
    }


    @Test
    fun getNextMatchDataSuccessTest() {
        val dataNextMatch = mutableSetOf<NextMatchItem>()
        val dataHomeBadge = mutableListOf<TeamDetailResponse>()
        val dataAwayBadge = mutableListOf<TeamDetailResponse>()
        val listNextSchedule = mutableListOf<NextMatchItem>()
        val listHomeBadge = mutableListOf<String>()
        val listAwayBadge = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()

        schedulePresenter.getNextSchedule(idLeague, idTeam)

        argumentCaptor<RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(scheduleRepository).getNextScheduleData(eq(idLeague), eq(idTeam), capture())
            firstValue.onDataLoaded(dataNextMatch, dataHomeBadge, dataAwayBadge)
        }

        listNextSchedule.addAll(dataNextMatch)

        dataHomeBadge.map {
            listHomeBadge.add(it.teams[0].strTeamBadge)
            listStadium.add(it.teams[0].strStadium)
        }

        dataAwayBadge.map {
            listAwayBadge.add(it.teams[0].strTeamBadge)
        }

        verify(view).hideLoading()
        verify(view).loadNextSchedule(listNextSchedule, listHomeBadge, listAwayBadge, listStadium)
    }

    @Test
    fun getNextMatchDataErrorTest() {

        schedulePresenter.getNextSchedule("", "")

        argumentCaptor<RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>>().apply {
            verify(scheduleRepository).getNextScheduleData(eq(""), eq(""), capture())
            firstValue.onDataError("")
        }

        verify(view).hideLoading()
        verify(view).onFailure("")
    }
}