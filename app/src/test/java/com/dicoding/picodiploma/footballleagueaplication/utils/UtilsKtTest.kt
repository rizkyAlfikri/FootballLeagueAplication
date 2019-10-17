package com.dicoding.picodiploma.footballleagueaplication.utils

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class UtilsKtTest {

    @Test
    fun dateGMTFormat() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = "2000-6-9"
        val datesTest = dateFormat.parse(date)
        val sdf = SimpleDateFormat("EEE, dd-MMM-yyyy")

        assertEquals("09-Jun-2000",  sdf.format(datesTest))
    }

    @Test
    fun timeGMTFormat() {
        val time = "17:12:50"
        val formatter = SimpleDateFormat("HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime: String? = time
        val times = formatter.parse(dateTime)
        val sdf = SimpleDateFormat("HH:mm")

        assertEquals("00:12",sdf.format(times) )
    }
}