package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsResponse
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchResponse
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

 @GET("searchteams.php")
 fun getSearchTeamApi(@Query("t") query: String?): Observable<SearchTeamResponse>

 @GET("lookuphonors.php")
 fun getPlayerHonorsApi(@Query("id") idPlayer: String?): Observable<PlayerHonorsResponse>

 @GET("lookupplayer.php")
 fun getPlayerDetailApi(@Query("id") idPlayer: String?): Observable<PlayerDetailResponse>

 @GET("lookup_all_teams.php")
 fun getTeamFromServer(@Query("id") idLeague: String?): Observable<TeamResponse>

 @GET("lookupteam.php")
 fun getTeamDetailFromServer(@Query("id") idTeam: String?): Observable<TeamDetailResponse>

 @GET("lookup_all_players.php")
 fun getPlayer(@Query("id") idTeam: String?): Observable<PlayerResponse>

 @GET("lookuptable.php")
 fun getTableTeam(@Query("l") idLeague: String?): Observable<TeamTableResponse>

 @GET("eventspastleague.php")
 fun getLastMatch(@Query("id") idLeague: String?): Observable<LastMatchResponse>

 @GET("eventsnextleague.php")
 fun getNextMatch(@Query("id") idLeague: String?): Observable<NextMatchResponse>

 @GET("lookupleague.php")
 fun getLeagueDetailApi(@Query("id") idLeague: String?): Observable<LeagueDetailResponse>

 @GET("lookupevent.php")
 fun getMatchDetailApi(@Query("id") idEvent: String?): Observable<MatchDetailResponse>

 @GET("searchevents.php")
 fun getSearchMatch(@Query("e") query: String?): Observable<SearchMatchResponse>
}