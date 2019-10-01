package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem

interface LastMatchView {
    fun showLoading()
    fun hideLoading()
    fun loadLastMatch(
        data: List<LastMatchItem>,
        dataHome: MutableList<String>,
        dataAway: MutableList<String>,
        dataDate: Set<String>)
    fun onFailure(throwable: Throwable)
}