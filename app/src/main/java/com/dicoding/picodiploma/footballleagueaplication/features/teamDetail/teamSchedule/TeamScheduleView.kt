package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem

interface TeamScheduleView {
    fun showLoading()
    fun hideLoading()
    fun loadLastTeam(
        listLastMatch: MutableList<LastMatchItem>,
        listLastBadgeHome: MutableList<String>,
        listLastBadgeAway: MutableList<String>,
        listLastStadiumHome: MutableList<String?>
    )

    //    fun loadLastAwayTeam(listLastBadgeAway: MutableList<String>)
    fun loadNextTeam(
        listNextMatch: MutableList<NextMatchItem>,
        listNextBadgeHome: MutableList<String>,
        listNextBadgeAway: MutableList<String>,
        listNextStadiumHome: MutableList<String?>

    )
    fun onFailure(throwable: Throwable)
}