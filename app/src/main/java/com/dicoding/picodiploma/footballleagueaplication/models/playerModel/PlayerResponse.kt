package com.dicoding.picodiploma.footballleagueaplication.models.playerModel


import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("player")
    var player: List<PlayerItem>
)