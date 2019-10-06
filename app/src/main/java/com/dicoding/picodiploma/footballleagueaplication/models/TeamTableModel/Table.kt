package com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel


import com.google.gson.annotations.SerializedName

data class Table(
    @SerializedName("draw")
    var draw: Int,
    @SerializedName("goalsagainst")
    var goalsagainst: Int,
    @SerializedName("goalsdifference")
    var goalsdifference: Int,
    @SerializedName("goalsfor")
    var goalsfor: Int,
    @SerializedName("loss")
    var loss: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("played")
    var played: Int,
    @SerializedName("teamid")
    var teamid: String,
    @SerializedName("total")
    var total: Int,
    @SerializedName("win")
    var win: Int
)