package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApiService {

 @GET("lookup_all_teams.php")
 fun getTeamFromServer(@Query("id") idLeague: String?): Observable<TeamResponse>

 @GET("lookupteam.php")
 fun getTeamDetailFromServer(@Query("id") idTeam: String?): Observable<TeamDetailResponse>

 @GET("lookup_all_players.php")
 fun getPlayer(@Query("id") idTeam: String?): Observable<PlayerResponse>

 @GET("lookuptable.php")
 fun getTableTeam(@Query("l") idLeague: String?, @Query("s") idSeason: String?): Observable<TeamTableResponse>

}