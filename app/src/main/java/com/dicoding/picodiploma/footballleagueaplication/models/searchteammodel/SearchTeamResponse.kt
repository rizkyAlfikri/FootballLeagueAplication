package com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel


import com.google.gson.annotations.SerializedName

data class SearchTeamResponse(
    @SerializedName("teams")
    var teams: List<SearchTeamItem>
)