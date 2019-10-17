package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview

import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.TeamTableResponse
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.dicoding.picodiploma.footballleagueaplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import java.net.ConnectException

class TeamOverviewPresenter(
    private val view: TeamOverviewView,
    private val gson: Gson,
    private val apiRepository: ApiRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamOverviewData(idLeague: String?, idSeason: String, idTeam: String?) {
        val listTable = mutableListOf<Table>()

        view.showLoading()

        GlobalScope.launch(context.main) {

            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getTeamDetail(idTeam)).await(),
                TeamDetailResponse::class.java
            )

            val dataTable = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getTableTeam(idLeague, idSeason)).await(),
                TeamTableResponse::class.java
            )

            dataTable.table.map {
                if (idTeam == it.teamid) {
                    listTable.add(it)
                }
            }

            try {
                view.loadTeamDetailToView(data.teams)
            } catch (e: ConnectException) {
                view.onFailure(e)
            } catch (e: NullPointerException) {
                view.onFailure(e)
            }


            try {
                view.loadTeamTableToView(listTable)
            } catch (e: ConnectException) {
                view.onFailure(e)
            } catch (e: NullPointerException) {
                view.onFailure(e)
            }

            view.hideLoading()
        }
    }
}