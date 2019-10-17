package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.BuildConfig

// this class for set up an address that will be uses in when request data to server
object TheSportDb {

    fun getTableTeam(idLeague: String?, idSeason: String?): String {
        return BuildConfig.BASE_URL + "lookuptable.php?l=$idLeague&s=$idSeason"
    }
    fun getAllTeam(idLeague: String?): String {
        return BuildConfig.BASE_URL + "lookup_all_teams.php?id=$idLeague"
    }

    fun getPlayer(idTeam: String?): String {
        return BuildConfig.BASE_URL + "lookup_all_players.php?id=$idTeam"
    }

    fun getSearchMatch(query: String?): String {
        return BuildConfig.BASE_URL + "searchevents.php?e=$query"
    }

    fun getDetailMatch(idEvent: String?): String {
        return BuildConfig.BASE_URL + "lookupevent.php?id=$idEvent"
    }

    fun getDetailLeague(idLeague: String?): String {
        return BuildConfig.BASE_URL + "lookupleague.php?id=$idLeague"
    }

    fun getLastMatch(idLeague: String?): String {
        return BuildConfig.BASE_URL + "eventspastleague.php?id=$idLeague"
    }

    fun getNextMatch(idLeague: String?): String {
        return BuildConfig.BASE_URL + "eventsnextleague.php?id=$idLeague"
    }

    fun getTeamDetail(idTeam: String?): String {
        return BuildConfig.BASE_URL + "lookupteam.php?id=$idTeam"
    }
}