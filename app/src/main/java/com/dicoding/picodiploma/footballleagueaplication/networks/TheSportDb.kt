package com.dicoding.picodiploma.footballleagueaplication.networks

import android.net.Uri
import com.dicoding.picodiploma.footballleagueaplication.BuildConfig

// this class for set up an address that will be uses in when request data to server
object TheSportDb {

    fun getSearchMatch(query: String?): String {
        return Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath("searchevents.php")
            .appendQueryParameter("e", query)
            .build()
            .toString()
    }

    fun getDetailMatch(idEvent: String?): String {
        return Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath("lookupevent.php")
            .appendQueryParameter("id", idEvent)
            .build()
            .toString()
    }

    fun getDetailLeague(idLeague: String?): String {
        return Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath("lookupleague.php")
            .appendQueryParameter("id", idLeague)
            .build()
            .toString()
    }

    fun getLastMatch(idLeague: String?): String {
        return Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath("eventspastleague.php")
            .appendQueryParameter("id", idLeague)
            .build()
            .toString()
    }

    fun getNextMatch(idLeague: String?): String {
        return Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id", idLeague)
            .build()
            .toString()
    }

    fun getTeamDetail(idTeam: String?): String {
        return Uri.parse(BuildConfig.BASE_URL)
            .buildUpon()
            .appendPath("lookupteam.php")
            .appendQueryParameter("id", idTeam)
            .build()
            .toString()
    }
}