package com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel


import com.google.gson.annotations.SerializedName

data class MatchDetailResponse(
    @SerializedName("events")
    var events: List<MatchDetailItem>
)