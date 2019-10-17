package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.networks.TheSportDb
import com.dicoding.picodiploma.footballleagueaplication.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import java.net.ConnectException


class TeamDetailPresenter(
    private val view: TeamDetailView,
    private val gson: Gson,
    private val apiRepository: ApiRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamDetailData(idTeam: String) {

        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDb.getTeamDetail(idTeam)).await(),
                TeamDetailResponse::class.java
            )


            view.hideLoading()
            try {
                view.loadToView(data.teams)
            } catch (e: ConnectException) {
                view.onFailure(e)
            } catch (e: NullPointerException) {
                view.onFailure(e)
            }
        }
    }
}