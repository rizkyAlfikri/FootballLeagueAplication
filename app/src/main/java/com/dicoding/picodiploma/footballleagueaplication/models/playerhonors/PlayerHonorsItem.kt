package com.dicoding.picodiploma.footballleagueaplication.models.playerhonors


import com.google.gson.annotations.SerializedName

data class PlayerHonorsItem(
    @SerializedName("id")
    var id: String,
    @SerializedName("idPlayer")
    var idPlayer: String,
    @SerializedName("idTeam")
    var idTeam: String?,
    @SerializedName("strHonour")
    var strHonour: String?,
    @SerializedName("strPlayer")
    var strPlayer: String,
    @SerializedName("strSeason")
    var strSeason: String?,
    @SerializedName("strSport")
    var strSport: String,
    @SerializedName("strTeam")
    var strTeam: String?
)