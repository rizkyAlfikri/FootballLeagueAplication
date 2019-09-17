package com.dicoding.picodiploma.footballleagueaplication.fragments.last_match_fragment

import com.dicoding.picodiploma.footballleagueaplication.models.last.LastMatchItem

interface LastMatchView {
    fun showLoading()
    fun hideLoading()
    fun loadLastMatch(data: List<LastMatchItem>)
    fun loadHomeBadge(dataHome: MutableList<String>)
    fun loadAwayBadge(dataAway: MutableList<String>)
    fun onFailure(throwable: Throwable)
}