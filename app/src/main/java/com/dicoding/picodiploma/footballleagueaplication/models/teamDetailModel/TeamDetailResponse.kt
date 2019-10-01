package com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel


import com.google.gson.annotations.SerializedName

data class TeamDetailResponse(
    @SerializedName("teams")
    var teams: List<TeamDetailItem>
)