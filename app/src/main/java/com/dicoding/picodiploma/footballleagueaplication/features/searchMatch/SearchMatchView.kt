package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch

import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()


    fun loadSearchDataToView(
        dataSearch: List<SearchMatchItem>,
        dataHome: MutableList<String>,
        dataAway: MutableList<String>,
        dataStadium: MutableList<String?>)


    fun onFailure(throwable: String)
}