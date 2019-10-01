package com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel


import com.google.gson.annotations.SerializedName

data class NextMatchResponse(
    @SerializedName("events")
    var events: List<NextMatchItem>
)