package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem

interface TeamScheduleView {
    fun showLoading()
    fun hideLoading()
    fun getScheduleData(listLastMatch: List<LastMatchItem>, listNextMatchItem: List<NextMatchItem>)
    fun onFailure(throwable: Throwable?)
}