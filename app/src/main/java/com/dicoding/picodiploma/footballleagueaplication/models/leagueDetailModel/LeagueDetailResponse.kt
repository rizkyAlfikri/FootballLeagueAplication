package com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel


import com.google.gson.annotations.SerializedName

data class LeagueDetailResponse(
    @SerializedName("leagues")
    var leagues: List<LeagueDetailItem>
)