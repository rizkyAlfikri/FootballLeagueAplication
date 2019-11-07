package com.dicoding.picodiploma.footballleagueaplication.models.playerhonors


import com.google.gson.annotations.SerializedName

data class PlayerHonorsResponse(
    @SerializedName("honors")
    var honors: List<PlayerHonorsItem>
)