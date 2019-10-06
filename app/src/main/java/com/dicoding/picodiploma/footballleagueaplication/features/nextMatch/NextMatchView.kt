package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem

interface NextMatchView {

    fun showLoading()
    fun hideLoading()
    fun loadDataToView(
        dataNextMatch: List<NextMatchItem>,
        dataHome: MutableList<String>,
        dataAway: MutableList<String>,
        dataStadium: MutableList<String?>,
        dataDate: MutableSet<String>
    )

    fun onFailure(throwable: Throwable)
}