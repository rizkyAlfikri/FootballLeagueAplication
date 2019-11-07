package com.dicoding.picodiploma.footballleagueaplication.db


// make table for database with data class
data class FavoritesModel(
    val id: Long?,
    val idEvent: String?,
    val date: String?,
    val time: String?,
    val homeTeam: String?,
    val awayTeam: String?,
    val homeScore: String?,
    val awayScore: String?,
    val homeBadge: String?,
    val awayBadge: String?,
    val idLeague: String?
) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val DATE: String = "DATE"
        const val TIME: String = "TIME"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_BADGE: String = "HOME_BADGE"
        const val AWAY_BADGE: String = "AWAY_BADGE"
        const val ID_LEAGUE: String = "ID_LEAGUE"
    }

}