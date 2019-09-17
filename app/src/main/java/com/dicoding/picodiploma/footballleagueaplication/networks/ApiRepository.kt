package com.dicoding.picodiploma.footballleagueaplication.networks

import java.net.URL

class ApiRepository {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}