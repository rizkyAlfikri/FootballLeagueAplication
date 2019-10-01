package com.dicoding.picodiploma.footballleagueaplication.networks

import java.net.URL

// this class for make request to server
class ApiRepository {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}