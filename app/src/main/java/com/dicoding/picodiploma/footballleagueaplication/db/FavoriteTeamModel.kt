package com.dicoding.picodiploma.footballleagueaplication.db

data class FavoriteTeamModel(
    val id: Long?,
    val idTeam: String?,
    val idLeague: String?,
    val teamName: String?,
    val leagueName: String?,
    val teamBadge: String?,
    val winTeam: Int?,
    val drawTeam: Int?,
    val lostTeam: Int?
) {

    companion object {
        const val TABLE_TEAM_FAVORITE: String = "TABLE_TEAM_FAVORITE"
        const val ID: String = "_ID"
        const val ID_TEAM: String = "ID_TEAM"
        const val ID_LEAGUE: String = "ID_LEAGUE"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val LEAGUE_NAME: String = "LEAGUE_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val WIN_TEAM: String = "WIN_TEAM"
        const val DRAW_TEAM: String = "DRAW_TEAM"
        const val LOST_TEAM: String = "LOST_TEAM"
    }
}