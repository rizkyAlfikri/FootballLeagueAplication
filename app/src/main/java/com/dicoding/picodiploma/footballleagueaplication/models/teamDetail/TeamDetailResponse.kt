package com.dicoding.picodiploma.footballleagueaplication.models.teamDetail


import com.google.gson.annotations.SerializedName

data class TeamDetailResponse(
    @SerializedName("teams")
    var teams: List<Team>
)