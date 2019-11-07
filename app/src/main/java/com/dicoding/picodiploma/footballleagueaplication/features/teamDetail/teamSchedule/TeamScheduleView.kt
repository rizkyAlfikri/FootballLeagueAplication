package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem

interface TeamScheduleView {
    fun showLoading()
    fun hideLoading()
    fun loadLastSchedule(
        listLastMatch: MutableList<LastMatchItem>,
        listLastBadgeHome: MutableList<String>,
        listLastBadgeAway: MutableList<String>,
        listLastStadium: MutableList<String?>
    )

    //    fun loadLastAwayTeam(listLastBadgeAway: MutableList<String>)
    fun loadNextSchedule(
        listNextMatch: MutableList<NextMatchItem>,
        listNextBadgeHome: MutableList<String>,
        listNextBadgeAway: MutableList<String>,
        listNextStadium: MutableList<String?>

    )
    fun onFailure(throwable: String)
}