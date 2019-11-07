package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem

interface LastMatchView {
    fun showLoading()
    fun hideLoading()

    fun loadLastMatchData(
        listLastMatch: List<LastMatchItem>,
        listHomeBadge: List<String>,
        listAwayBadge: List<String>,
        listStadium: List<String?>,
        setDate: Set<String>
    )

    fun onFailure(throwable: String)
}