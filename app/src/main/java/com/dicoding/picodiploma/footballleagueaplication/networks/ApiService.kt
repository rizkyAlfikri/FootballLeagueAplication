package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.models.last.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetail.TeamDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiService {

//    @GET("lookupleague.php")
//    fun getDetailLeague(@Query("id") idLeague: String): Call<LeagueResponse>
//
//    @GET("eventsnextleague.php")
//    fun getNextMatch(@Query("id") idLeague: String): Call<LeagueResponse>
//
//    @GET("eventspastleague.php")
//    fun getLastLeague(@Query("id") idLeague: String?): Call<LastMatchResponse>
//
//    @GET("lookupevent.php")
//    fun getDetailMatch(@Query("id") idLeague: String): Call<LeagueResponse>
//
//    @GET("searchevents.php")
//    fun getSearchMatch(@Query("e") teamsName: String): Call<LeagueResponse>
//
//    @GET("lookupteam.php")
//    fun getDetailTeam(@Query("id") idTeam: String?): Call<TeamDetailResponse>
}