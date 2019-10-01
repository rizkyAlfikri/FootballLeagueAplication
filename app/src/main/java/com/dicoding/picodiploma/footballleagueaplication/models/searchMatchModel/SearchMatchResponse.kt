package com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel


import com.google.gson.annotations.SerializedName

data class SearchMatchResponse(
    @SerializedName("event")
    var event: List<SearchMatchItem>
)