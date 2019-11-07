package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem

interface NextMatchView {

    fun showLoading()
    fun hideLoading()

    fun loadNextMatchData(
        listNextMatch: List<NextMatchItem>,
        listHomeBadge: List<String>,
        listAwayBadge: List<String>,
        listStadium: List<String?>,
        setDate: Set<String>
    )

    fun onFailure(throwable: String)
}