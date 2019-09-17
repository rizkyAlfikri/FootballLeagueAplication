package com.dicoding.picodiploma.footballleagueaplication

interface BaseViewInterface {
    fun <S> loadDataToView(data: List<S>)
    fun onFailure(throwable: Throwable)
    fun showLoading()
    fun hideLoading()
}