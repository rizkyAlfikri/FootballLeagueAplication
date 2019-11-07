package com.dicoding.picodiploma.footballleagueaplication.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = countingIdlingResource

    fun incrementIdle() {
        countingIdlingResource.increment()
    }

    fun decrementIdle() {
        countingIdlingResource.decrement()
    }
}