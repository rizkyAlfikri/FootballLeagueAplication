package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch

import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailItem

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun loadDataToView(listData: List<MatchDetailItem>)
    fun loadHomeBadgeToView(urlHomeBadge: String)
    fun loadAwayBadgeToView(urlAwayBadge: String)
    fun onFailure(throwable: Throwable)
}