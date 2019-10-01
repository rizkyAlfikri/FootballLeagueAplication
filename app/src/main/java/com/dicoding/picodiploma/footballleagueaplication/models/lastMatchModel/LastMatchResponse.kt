package com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel


import com.google.gson.annotations.SerializedName

data class LastMatchResponse(
    @SerializedName("events")
    var lastMatchItems: List<LastMatchItem>
)