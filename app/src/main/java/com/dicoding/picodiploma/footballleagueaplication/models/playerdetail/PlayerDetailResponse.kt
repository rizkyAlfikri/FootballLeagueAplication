package com.dicoding.picodiploma.footballleagueaplication.models.playerdetail


import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse(
    @SerializedName("players")
    var players: List<PlayerDetailItem>
)