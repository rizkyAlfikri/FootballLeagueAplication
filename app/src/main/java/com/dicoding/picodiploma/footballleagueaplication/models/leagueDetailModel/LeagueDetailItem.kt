package com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel


import com.google.gson.annotations.SerializedName

data class LeagueDetailItem(
    @SerializedName("dateFirstEvent")
    var dateFirstEvent: String,
    @SerializedName("idLeague")
    var idLeague: String,
    @SerializedName("idSoccerXML")
    var idSoccerXML: String,
    @SerializedName("intFormedYear")
    var intFormedYear: String,
    @SerializedName("strBadge")
    var strBadge: String,
    @SerializedName("strBanner")
    var strBanner: String,
    @SerializedName("strCountry")
    var strCountry: String,
    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String,
    @SerializedName("strFacebook")
    var strFacebook: String,
    @SerializedName("strFanart1")
    var strFanart1: String,
    @SerializedName("strFanart2")
    var strFanart2: String,
    @SerializedName("strFanart3")
    var strFanart3: String,
    @SerializedName("strFanart4")
    var strFanart4: String,
    @SerializedName("strLeague")
    var strLeague: String?,
    @SerializedName("strLocked")
    var strLocked: String,
    @SerializedName("strPoster")
    var strPoster: String,
    @SerializedName("strSport")
    var strSport: String,
    @SerializedName("strTrophy")
    var strTrophy: String,
    @SerializedName("strTwitter")
    var strTwitter: String,
    @SerializedName("strWebsite")
    var strWebsite: String,
    @SerializedName("strYoutube")
    var strYoutube: String
)