package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer

import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerItem

interface TeamPlayerView {
    fun showLoading()
    fun hideLoading()
    fun loadPlayerToView(dataFormation: Set<String>, dataPlayer: List<PlayerItem>)
    fun onFailure(throwable: Throwable)
}