package com.dicoding.picodiploma.footballleagueaplication.repository

import com.dicoding.picodiploma.footballleagueaplication.RxImmediateSchedulerRule
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
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

class TeamScheduleRepositoryTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testScheduler = RxImmediateSchedulerRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var callbackLast: RepositoryMultipleCallback<Set<LastMatchItem>, List<TeamDetailResponse>>

    @Mock
    private lateinit var callbackNext: RepositoryMultipleCallback<Set<NextMatchItem>, List<TeamDetailResponse>>

    @Mock
    private lateinit var lastMatchResponse: LastMatchResponse

    @Mock
    private lateinit var nextMatchResponse: NextMatchResponse

    @Mock
    private lateinit var teamDetailResponse: TeamDetailResponse

    private lateinit var teamScheduleRepository: TeamScheduleRepository

    private val idLeague = "4328"

    private val idTeam = "133604"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamScheduleRepository = TeamScheduleRepository()
    }

    @Test
    fun getLastScheduleDataSuccessTest() {

        Mockito.`when`(apiService.getLastMatch(idLeague))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualLastSchedule: Observable<LastMatchResponse> =
            apiService.getLastMatch(idLeague)

        val testObservableLastSchedule: TestObserver<LastMatchResponse> = actualLastSchedule.test()
        testObservableLastSchedule.assertSubscribed()
        testObservableLastSchedule.assertResult(lastMatchResponse)

        teamScheduleRepository.getLastScheduleData(idLeague, idTeam, callbackLast)
    }

    @Test
    fun getLastScheduleDataErrorTest() {

        Mockito.`when`(apiService.getLastMatch(""))
            .thenReturn(Observable.just(lastMatchResponse))

        val actualObserverLastSchedule: Observable<LastMatchResponse> =
            apiService.getLastMatch("")

        val testObserverLastSchedule: TestObserver<LastMatchResponse> =
            actualObserverLastSchedule.test()
        testObserverLastSchedule.assertSubscribed()
        testObserverLastSchedule.assertResult(lastMatchResponse)

        teamScheduleRepository.getLastScheduleData("", "", callbackLast)
    }

    @Test
    fun getNextScheduleDataSuccessTest() {

        Mockito.`when`(apiService.getNextMatch(idLeague))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualObserverNextSchedule: Observable<NextMatchResponse> =
            apiService.getNextMatch(idLeague)

        val testObservableNextSchedule: TestObserver<NextMatchResponse> =
            actualObserverNextSchedule.test()
        testObservableNextSchedule.assertSubscribed()
        testObservableNextSchedule.assertResult(nextMatchResponse)

        teamScheduleRepository.getNextScheduleData(idLeague, idTeam, callbackNext)
    }

    @Test
    fun getNextScheduleDataErrorTest() {

        Mockito.`when`(apiService.getNextMatch(""))
            .thenReturn(Observable.just(nextMatchResponse))

        val actualObserverNextSchedule: Observable<NextMatchResponse> =
            apiService.getNextMatch("")

        val testObservableNextSchedule: TestObserver<NextMatchResponse> =
            actualObserverNextSchedule.test()
        testObservableNextSchedule.assertSubscribed()
        testObservableNextSchedule.assertResult(nextMatchResponse)

        teamScheduleRepository.getNextScheduleData("", "", callbackNext)
    }

    @Test
    fun getLastScheduleBadgeDataSuccessTest() {

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualObserverLastBadge: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer(idTeam)

        val testObservableLastBadge: TestObserver<TeamDetailResponse> = actualObserverLastBadge.test()
        testObservableLastBadge.assertSubscribed()
        testObservableLastBadge.assertResult(teamDetailResponse)

        teamScheduleRepository.getLastScheduleData(idLeague, idTeam, callbackLast)
    }

    @Test
    fun getLastScheduleBadgeDataErrorTest() {

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualObserverLastBadge: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer("")

        val testObservableLastBadge: TestObserver<TeamDetailResponse> = actualObserverLastBadge.test()
        testObservableLastBadge.assertSubscribed()
        testObservableLastBadge.assertResult(teamDetailResponse)

        teamScheduleRepository.getLastScheduleData("", "", callbackLast)
    }

    @Test
    fun getNextScheduleBadgeDataSuccessTest() {

        Mockito.`when`(apiService.getTeamDetailFromServer(idTeam))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualObserverNextBadge: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer(idTeam)

        val testObserverNextBadge: TestObserver<TeamDetailResponse> = actualObserverNextBadge.test()
        testObserverNextBadge.assertSubscribed()
        testObserverNextBadge.assertResult(teamDetailResponse)

        teamScheduleRepository.getNextScheduleData(idLeague, idTeam, callbackNext)
    }


    @Test
    fun getNextScheduleBadgeDataErrorTest() {

        Mockito.`when`(apiService.getTeamDetailFromServer(""))
            .thenReturn(Observable.just(teamDetailResponse))

        val actualObserverNextBadge: Observable<TeamDetailResponse> =
            apiService.getTeamDetailFromServer("")

        val testObserverNextBadge: TestObserver<TeamDetailResponse> = actualObserverNextBadge.test()
        testObserverNextBadge.assertSubscribed()
        testObserverNextBadge.assertResult(teamDetailResponse)

        teamScheduleRepository.getNextScheduleData("", "", callbackNext)
    }

}