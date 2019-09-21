package com.dicoding.picodiploma.footballleagueaplication.models.leagueDetail


import com.google.gson.annotations.SerializedName

data class LeagueDetailResponse(
    @SerializedName("leagues")
    var leagues: List<League>
)