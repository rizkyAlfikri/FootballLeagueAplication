package com.dicoding.picodiploma.footballleagueaplication.features.teams

import android.annotation.SuppressLint
import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.RepositoryCallback
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamRepository


class TeamPresenter(
    private val view: TeamView,
    private val teamRepository: TeamRepository
) {

    @SuppressLint("CheckResult")
    fun getTeamData(idLeague: String?) {
        view.showLoading()

        teamRepository.getTeamList(idLeague, object: RepositoryCallback<TeamResponse> {
            override fun onDataLoaded(data: TeamResponse) {
                view.loadDataToView(data)
                view.hideLoading()
            }

            override fun onDataError(throwable: String) {
                view.onFailure(throwable)
                view.hideLoading()
            }
        })
    }
}