package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem

interface NextMatchView {

    fun showLoading()
    fun hideLoading()
    fun loadDataToView(
        data: List<NextMatchItem>,
        dataDate: Set<String>
    )

    fun loadHomeTeam(
        dataHome: MutableList<String>,
        dataStadium: MutableList<String?>
    )

    fun loadAwayTeam(
        dataAway: MutableList<String>
    )


    fun onFailure(throwable: String)
}