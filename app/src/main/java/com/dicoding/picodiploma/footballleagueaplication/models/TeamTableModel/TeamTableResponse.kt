package com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel


import com.google.gson.annotations.SerializedName

data class TeamTableResponse(
    @SerializedName("table")
    var table: List<Table>
)