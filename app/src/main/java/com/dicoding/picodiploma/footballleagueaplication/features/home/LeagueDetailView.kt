package com.dicoding.picodiploma.footballleagueaplication.features.home

import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun loadDataToView(listData: LeagueDetailResponse)
//    fun loadBannerToView(listBanner: MutableList<String>)
    fun onFailure(throwable: Throwable)

}