package com.dicoding.picodiploma.footballleagueaplication.features.playerdetail

import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsItem

interface PlayerDetailView {

    fun showLoading()
    fun hideLoading()
    fun loadPlayerToView(listPlayerDetail: List<PlayerDetailItem>)
    fun loadPlayerHonorToView(listPlayerHonorsItem: List<PlayerHonorsItem>)
    fun onFailure(throwable: String)
}