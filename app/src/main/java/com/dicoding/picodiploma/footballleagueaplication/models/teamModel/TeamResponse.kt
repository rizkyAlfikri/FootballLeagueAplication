package com.dicoding.picodiploma.footballleagueaplication.models.teamModel


import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    var teams: List<TeamItem>
)