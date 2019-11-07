package com.dicoding.picodiploma.footballleagueaplication.repository

interface RepositoryCallback<T> {
    fun onDataLoaded(data: T)

    fun onDataError(throwable: String)
}