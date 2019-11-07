package com.dicoding.picodiploma.footballleagueaplication.repository

interface RepositoryCallbackTable<T, S> {
    fun onDataLoaded(dataTable: T, dataTeamBadge: S)

    fun onDataError(throwable: String)
}