package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.BuildConfig
import org.junit.Test

import org.junit.Assert.*

class TheSportDbTest {

    private val idLeague = "4328"
    private val idSeason = "1213"
    private val idTeam = "133612"
    private val query = "Arsenal"
    private val idEvent = "441613"

    @Test
    fun getTableTeam() {
        val actualTableTeam = BuildConfig.BASE_URL + "lookuptable.php?l=$idLeague&s=$idSeason"
        val expectedTableTeam = "https://www.thesportsdb.com/api/v1/json/1/lookuptable.php?l=4328&s=1213"
        assertEquals(expectedTableTeam, actualTableTeam)
    }

    @Test
    fun getAllTeam() {
        val actualAllTeam = BuildConfig.BASE_URL + "lookup_all_teams.php?id=$idLeague"
        val expectedAllTeam = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=4328"
        assertEquals(expectedAllTeam, actualAllTeam)
    }

    @Test
    fun getPlayer() {
        val actualPlayer = BuildConfig.BASE_URL + "lookup_all_players.php?id=$idTeam"
        val expectedPlayer = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=133612"
        assertEquals(expectedPlayer, actualPlayer)
    }

    @Test
    fun getSearchMatch() {
        val actualSearchMatch = BuildConfig.BASE_URL + "searchevents.php?e=$query"
        val expectedSearchMatch = "https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=Arsenal"
        assertEquals(expectedSearchMatch, actualSearchMatch)
    }

    @Test
    fun getDetailMatch() {
        val actualDetailMatch = BuildConfig.BASE_URL + "lookupevent.php?id=$idEvent"
        val expectedDetailMatch = "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=441613"
        assertEquals(expectedDetailMatch, actualDetailMatch)
    }

    @Test
    fun getDetailLeague() {
        val actualDetailLeague = BuildConfig.BASE_URL + "lookupleague.php?id=$idLeague"
        val expectedDetailLeague = "https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id=4328"
        assertEquals(expectedDetailLeague, actualDetailLeague)
    }

    @Test
    fun getLastMatch() {
        val actualLastMatch = BuildConfig.BASE_URL + "eventspastleague.php?id=$idLeague"
        val expectedLastMatch = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        assertEquals(expectedLastMatch, actualLastMatch)
    }

    @Test
    fun getNextMatch() {
        val actualNextMatch = BuildConfig.BASE_URL + "eventsnextleague.php?id=$idLeague"
        val expectedNextMatch = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        assertEquals(expectedNextMatch, actualNextMatch)
    }

    @Test
    fun getTeamDetail() {
        val actualTeamDetail = BuildConfig.BASE_URL + "lookupteam.php?id=$idTeam"
        val expectedTeamDetail = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133612"
        assertEquals(expectedTeamDetail, actualTeamDetail)
    }
}