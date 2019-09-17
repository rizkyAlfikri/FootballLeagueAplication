package com.dicoding.picodiploma.footballleagueaplication.fragments.nextMatchFragment

import com.dicoding.picodiploma.footballleagueaplication.models.nextMatch.NextMatchItem

interface NextMatchView {

    fun showLoading()
    fun hideLoading()
    fun loadDataToView(data: List<NextMatchItem>)
    fun onFailure(throwable: Throwable)

    fun loadHomeBadge(dataHome: MutableList<String>)
    fun loadAwayBadge(dataAway: MutableList<String>)
}