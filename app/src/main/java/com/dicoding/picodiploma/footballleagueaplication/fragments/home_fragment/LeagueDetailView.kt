package com.dicoding.picodiploma.footballleagueaplication.fragments.home_fragment

import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetail.LeagueDetailResponse

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun loadDataToView(listData: LeagueDetailResponse)
    fun loadBannerToView(listBanner: MutableList<String>)
    fun onFailure(throwable: Throwable)

}