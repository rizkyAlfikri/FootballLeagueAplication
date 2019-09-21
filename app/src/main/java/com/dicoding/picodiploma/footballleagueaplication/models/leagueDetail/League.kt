package com.dicoding.picodiploma.footballleagueaplication.models.leagueDetail


import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("dateFirstEvent")
    var dateFirstEvent: String,
    @SerializedName("idCup")
    var idCup: String,
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
    @SerializedName("strComplete")
    var strComplete: String,
    @SerializedName("strCountry")
    var strCountry: String,
    @SerializedName("strDescriptionCN")
    var strDescriptionCN: Any?,
    @SerializedName("strDescriptionDE")
    var strDescriptionDE: Any?,
    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String,
    @SerializedName("strDescriptionES")
    var strDescriptionES: Any?,
    @SerializedName("strDescriptionFR")
    var strDescriptionFR: String,
    @SerializedName("strDescriptionHU")
    var strDescriptionHU: Any?,
    @SerializedName("strDescriptionIL")
    var strDescriptionIL: Any?,
    @SerializedName("strDescriptionIT")
    var strDescriptionIT: Any?,
    @SerializedName("strDescriptionJP")
    var strDescriptionJP: Any?,
    @SerializedName("strDescriptionNL")
    var strDescriptionNL: Any?,
    @SerializedName("strDescriptionNO")
    var strDescriptionNO: Any?,
    @SerializedName("strDescriptionPL")
    var strDescriptionPL: Any?,
    @SerializedName("strDescriptionPT")
    var strDescriptionPT: Any?,
    @SerializedName("strDescriptionRU")
    var strDescriptionRU: Any?,
    @SerializedName("strDescriptionSE")
    var strDescriptionSE: Any?,
    @SerializedName("strDivision")
    var strDivision: String,
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
    @SerializedName("strGender")
    var strGender: String,
    @SerializedName("strLeague")
    var strLeague: String,
    @SerializedName("strLeagueAlternate")
    var strLeagueAlternate: String,
    @SerializedName("strLocked")
    var strLocked: String,
    @SerializedName("strLogo")
    var strLogo: String,
    @SerializedName("strNaming")
    var strNaming: String,
    @SerializedName("strPoster")
    var strPoster: String,
    @SerializedName("strRSS")
    var strRSS: String,
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