package com.dicoding.picodiploma.footballleagueaplication.utils

import android.annotation.SuppressLint
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

@SuppressLint("SimpleDateFormat")
fun timeGMTFormat(time: String?): String? {

    val formatter = SimpleDateFormat("HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime: String? = time
    val times = formatter.parse(dateTime)
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(times)
}

@SuppressLint("SimpleDateFormat")
fun dateGMTFormat(date: String?): String? {

    val formatter = SimpleDateFormat("yyyy-MM-dd")
    formatter.timeZone = TimeZone.getTimeZone("GMT")
    val dateTime = date
    val dates = formatter.parse(dateTime)
    val sdf = SimpleDateFormat("EEEE, dd-MMM-yyyy")
    return sdf.format(dates)
}