package com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel


import com.google.gson.annotations.SerializedName

data class MatchDetailItem(
    @SerializedName("dateEvent")
    var dateEvent: String,
    @SerializedName("dateEventLocal")
    var dateEventLocal: Any?,
    @SerializedName("idAwayTeam")
    var idAwayTeam: String,
    @SerializedName("idEvent")
    var idEvent: String,
    @SerializedName("idHomeTeam")
    var idHomeTeam: String,
    @SerializedName("idLeague")
    var idLeague: String,
    @SerializedName("idSoccerXML")
    var idSoccerXML: String,
    @SerializedName("intAwayScore")
    var intAwayScore: String?,
    @SerializedName("intAwayShots")
    var intAwayShots: String?,
    @SerializedName("intHomeScore")
    var intHomeScore: String?,
    @SerializedName("intHomeShots")
    var intHomeShots: String?,
    @SerializedName("intRound")
    var intRound: String,
    @SerializedName("intSpectators")
    var intSpectators: String,
    @SerializedName("strAwayFormation")
    var strAwayFormation: String?,
    @SerializedName("strAwayGoalDetails")
    var strAwayGoalDetails: String?,
    @SerializedName("strAwayLineupDefense")
    var strAwayLineupDefense: String?,
    @SerializedName("strAwayLineupForward")
    var strAwayLineupForward: String?,
    @SerializedName("strAwayLineupGoalkeeper")
    var strAwayLineupGoalkeeper: String?,
    @SerializedName("strAwayLineupMidfield")
    var strAwayLineupMidfield: String?,
    @SerializedName("strAwayLineupSubstitutes")
    var strAwayLineupSubstitutes: String?,
    @SerializedName("strAwayRedCards")
    var strAwayRedCards: String?,
    @SerializedName("strAwayTeam")
    var strAwayTeam: String,
    @SerializedName("strAwayYellowCards")
    var strAwayYellowCards: String?,
    @SerializedName("strBanner")
    var strBanner: Any?,
    @SerializedName("strCircuit")
    var strCircuit: Any?,
    @SerializedName("strCity")
    var strCity: Any?,
    @SerializedName("strCountry")
    var strCountry: Any?,
    @SerializedName("strDate")
    var strDate: String,
    @SerializedName("strDescriptionEN")
    var strDescriptionEN: Any?,
    @SerializedName("strEvent")
    var strEvent: String,
    @SerializedName("strFanart")
    var strFanart: Any?,
    @SerializedName("strFilename")
    var strFilename: String,
    @SerializedName("strHomeFormation")
    var strHomeFormation: String?,
    @SerializedName("strHomeGoalDetails")
    var strHomeGoalDetails: String?,
    @SerializedName("strHomeLineupDefense")
    var strHomeLineupDefense: String?,
    @SerializedName("strHomeLineupForward")
    var strHomeLineupForward: String?,
    @SerializedName("strHomeLineupGoalkeeper")
    var strHomeLineupGoalkeeper: String?,
    @SerializedName("strHomeLineupMidfield")
    var strHomeLineupMidfield: String?,
    @SerializedName("strHomeLineupSubstitutes")
    var strHomeLineupSubstitutes: String?,
    @SerializedName("strHomeRedCards")
    var strHomeRedCards: String?,
    @SerializedName("strHomeTeam")
    var strHomeTeam: String,
    @SerializedName("strHomeYellowCards")
    var strHomeYellowCards: String?,
    @SerializedName("strLeague")
    var strLeague: String,
    @SerializedName("strLocked")
    var strLocked: String,
    @SerializedName("strMap")
    var strMap: Any?,
    @SerializedName("strPoster")
    var strPoster: Any?,
    @SerializedName("strResult")
    var strResult: Any?,
    @SerializedName("strSeason")
    var strSeason: String,
    @SerializedName("strSport")
    var strSport: String,
    @SerializedName("strTVStation")
    var strTVStation: Any?,
    @SerializedName("strThumb")
    var strThumb: Any?,
    @SerializedName("strTime")
    var strTime: String,
    @SerializedName("strTimeLocal")
    var strTimeLocal: Any?,
    @SerializedName("strTweet1")
    var strTweet1: Any?,
    @SerializedName("strTweet2")
    var strTweet2: Any?,
    @SerializedName("strTweet3")
    var strTweet3: Any?,
    @SerializedName("strVideo")
    var strVideo: Any?
)