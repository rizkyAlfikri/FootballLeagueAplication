package com.dicoding.picodiploma.footballleagueaplication.models.playerdetail


import com.google.gson.annotations.SerializedName

data class PlayerDetailItem(
    @SerializedName("dateBorn")
    var dateBorn: String?,
    @SerializedName("dateSigned")
    var dateSigned: String?,
    @SerializedName("idPlayer")
    var idPlayer: String,
    @SerializedName("idPlayerManager")
    var idPlayerManager: String,
    @SerializedName("idSoccerXML")
    var idSoccerXML: String,
    @SerializedName("idTeam")
    var idTeam: String,
    @SerializedName("idTeamNational")
    var idTeamNational: Any?,
    @SerializedName("intLoved")
    var intLoved: String,
    @SerializedName("intSoccerXMLTeamID")
    var intSoccerXMLTeamID: String,
    @SerializedName("strAgent")
    var strAgent: String,
    @SerializedName("strBanner")
    var strBanner: Any?,
    @SerializedName("strBirthLocation")
    var strBirthLocation: String?,
    @SerializedName("strCollege")
    var strCollege: Any?,
    @SerializedName("strCreativeCommons")
    var strCreativeCommons: String,
    @SerializedName("strCutout")
    var strCutout: String,
    @SerializedName("strDescriptionCN")
    var strDescriptionCN: Any?,
    @SerializedName("strDescriptionDE")
    var strDescriptionDE: Any?,
    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String,
    @SerializedName("strDescriptionES")
    var strDescriptionES: Any?,
    @SerializedName("strDescriptionFR")
    var strDescriptionFR: Any?,
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
    @SerializedName("strHeight")
    var strHeight: String?,
    @SerializedName("strInstagram")
    var strInstagram: String,
    @SerializedName("strKit")
    var strKit: String,
    @SerializedName("strLocked")
    var strLocked: String,
    @SerializedName("strNationality")
    var strNationality: String?,
    @SerializedName("strNumber")
    var strNumber: String,
    @SerializedName("strOutfitter")
    var strOutfitter: String,
    @SerializedName("strPlayer")
    var strPlayer: String,
    @SerializedName("strPosition")
    var strPosition: String,
    @SerializedName("strRender")
    var strRender: Any?,
    @SerializedName("strSide")
    var strSide: String,
    @SerializedName("strSigning")
    var strSigning: String,
    @SerializedName("strSport")
    var strSport: String,
    @SerializedName("strTeam")
    var strTeam: String?,
    @SerializedName("strTeamNational")
    var strTeamNational: Any?,
    @SerializedName("strThumb")
    var strThumb: String,
    @SerializedName("strTwitter")
    var strTwitter: String,
    @SerializedName("strWage")
    var strWage: String,
    @SerializedName("strWebsite")
    var strWebsite: String,
    @SerializedName("strWeight")
    var strWeight: String?,
    @SerializedName("strYoutube")
    var strYoutube: String
)