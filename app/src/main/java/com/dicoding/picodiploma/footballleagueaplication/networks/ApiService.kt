package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
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

 @GET("eventspastleague.php")
 fun getLastMatch(@Query("id") idLeague: String?): Observable<LastMatchResponse>

 @GET("eventsnextleague.php")
 fun getNextMatch(@Query("id") idLeague: String?): Observable<NextMatchResponse>


}