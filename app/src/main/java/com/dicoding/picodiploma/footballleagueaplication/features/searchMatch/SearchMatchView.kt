package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch

import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun loadDataToView(data: MutableList<SearchMatchItem>, dataHome: MutableList<String>, dataAway: MutableList<String>)
    fun onFailure(throwable: Throwable)
}