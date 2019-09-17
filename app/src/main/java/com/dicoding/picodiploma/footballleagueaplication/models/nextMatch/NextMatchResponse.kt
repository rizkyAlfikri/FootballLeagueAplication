package com.dicoding.picodiploma.footballleagueaplication.models.nextMatch


import com.google.gson.annotations.SerializedName

data class NextMatchResponse(
    @SerializedName("events")
    var events: List<NextMatchItem>
)