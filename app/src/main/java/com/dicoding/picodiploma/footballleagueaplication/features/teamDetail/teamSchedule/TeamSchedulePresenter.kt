package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService.createService
import rx.Observable
import rx.Observer
import rx.schedulers.Schedulers

class TeamSchedulePresenter(private val view: TeamScheduleView) {

    fun getMatchData(idLeague: String?, idTeam: String?) {
        view.showLoading()

        val listLastMatch = mutableListOf<LastMatchItem>()
        val listNextMatch = mutableListOf<NextMatchItem>()
        listLastMatch.clear()

        val apiService = createService()
        val lastObserver: Observable<LastMatchResponse> = apiService.getLastMatch(idLeague).subscribeOn(Schedulers.newThread())
        val nextObserver: Observable<NextMatchResponse> = apiService.getNextMatch(idLeague).subscribeOn(Schedulers.newThread())


        Observable.merge(lastObserver, nextObserver).subscribe(object : Observer<Any?> {
            override fun onError(e: Throwable?) {
                view.hideLoading()
                view.onFailure(e)
            }

            override fun onNext(t: Any?) {
                when (t) {
                    is NextMatchResponse -> {
                        t.events.map {
                            if (it.idHomeTeam == idTeam || it.idAwayTeam == idTeam) {
                                listNextMatch.add(it)
                            }
                        }
                    }

                    is LastMatchResponse -> {
                        t.lastMatchItems.map {
                            if (it.idHomeTeam == idTeam || it.idAwayTeam == idTeam) {
                                listLastMatch.add(it)
                            }
                        }
                    }
                }
                view.hideLoading()
                view.getScheduleData(listLastMatch, listNextMatch)
            }

            override fun onCompleted() {

            }
        })

    }
}