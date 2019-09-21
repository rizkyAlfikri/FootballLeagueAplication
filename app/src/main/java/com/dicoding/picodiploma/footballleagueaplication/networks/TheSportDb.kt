package com.dicoding.picodiploma.footballleagueaplication.networks

import android.net.Uri
import com.dicoding.picodiploma.footballleagueaplication.BuildConfig

object TheSportDb {

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