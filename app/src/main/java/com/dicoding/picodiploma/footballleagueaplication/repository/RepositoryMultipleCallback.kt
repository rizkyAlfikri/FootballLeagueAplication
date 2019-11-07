package com.dicoding.picodiploma.footballleagueaplication.repository

interface RepositoryMultipleCallback<T, S> {
    fun onDataLoaded(dataMatch: T, dataHomeBadge: S, dataAwayBadge: S)

    fun onDataError(throwable: String)
}