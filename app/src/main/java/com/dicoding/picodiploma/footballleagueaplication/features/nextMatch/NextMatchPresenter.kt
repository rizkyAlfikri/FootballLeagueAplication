package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService.createService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NextMatchPresenter(
    private val view: NextMatchView
) {

    fun getNextMatchData(idLeague: String?) {
        val listHome = mutableListOf<String>()
        val listAway = mutableListOf<String>()
        val listStadium = mutableListOf<String?>()
        val setDate = mutableSetOf<String>()
        val apiService = createService()

        view.showLoading()


//        apiService.getNextMatch(idLeague)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .flatMap { Observable.from(it.events) }
//            .flatMap {itNext ->
//                Observable.zip(
//                    apiService.getTeamDetailFromServer(itNext.idHomeTeam),
//                    apiService.getTeamDetailFromServer(itNext.idAwayTeam)
//                ) { homeTeam: TeamDetailResponse, awayTeam: TeamDetailResponse ->
//                    listHome.add(homeTeam.teams[0].strTeamBadge)
//                    listAway.add(awayTeam.teams[0].strTeamBadge)
//                    listNext.add(itNext)
//                    setDate.add(itNext.dateEvent)
//                    listStadium.add(homeTeam.teams[0].strStadium)
//                }
//            }.doOnCompleted {
//                view.loadDataToView(listNext, listHome, listAway, listStadium, setDate )
//                view.hideLoading()
//            }


        apiService.getNextMatch(idLeague)
            .flatMap { Observable.from(it.events) }
            .flatMap { apiService.getTeamDetailFromServer(it.idHomeTeam) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnCompleted {
                view.loadHomeTeam(listHome, listStadium)
            }
            .subscribe(
                { it ->
                    if (it != null) {
                        it.teams.map {
                            listHome.add(it.strTeamBadge)
                            listStadium.add(it.strStadium)
                        }
                    } else {
                        view.onFailure("Team Home Badge Null")
                    }
                }, {
                    view.onFailure("Team Home Badge Connection error")
                }
            )

        apiService.getNextMatch(idLeague)
            .flatMap { itResponse -> Observable.from(itResponse.events) }
            .flatMap { itNext ->
                apiService.getTeamDetailFromServer(itNext.idAwayTeam)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnCompleted {
                view.loadAwayTeam(listAway)
            }
            .subscribe({ it ->
                if (it != null) {
                    it.teams.map {
                        listAway.add(it.strTeamBadge)
                    }
                } else {
                    view.onFailure("Team Away Badge Null")
                }
            },
                {
                    view.onFailure("Team Away Badge Connection Error")
                })



        apiService.getNextMatch(idLeague)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnCompleted { view.hideLoading() }
            .subscribe({ it ->
                if (it != null) {
                    it.events.map {
                        setDate.add(it.dateEvent)
                    }
                    view.loadDataToView(it.events, setDate)
                } else {
                    view.onFailure("Next match data is null")
                }
            },
                {
                    view.onFailure("Connection error")
                })
    }
}
