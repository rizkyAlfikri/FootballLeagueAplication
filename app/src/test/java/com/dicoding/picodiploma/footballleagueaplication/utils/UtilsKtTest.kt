package com.dicoding.picodiploma.footballleagueaplication.utils

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class UtilsKtTest {

    @Test
    fun dateGMTFormatTest() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = "2000-6-9"
        val datesTest = dateFormat.parse(date)
        val sdf = SimpleDateFormat("EEE, dd-MMM-yyyy")

        assertEquals("Fri, 09-Jun-2000",  sdf.format(datesTest))
    }

    @Test
    fun timeGMTFormatTest() {
        val time = "17:12:50"
        val formatter = SimpleDateFormat("HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime: String? = time
        val times = formatter.parse(dateTime)
        val sdf = SimpleDateFormat("HH:mm")

        assertEquals("00:12",sdf.format(times) )
    }

    @Test
    fun datePlayerInfoFormat() {
        val dateBorn = "1990-08-12"
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        formatter.timeZone = TimeZone.getTimeZone("GMT")
        val date = formatter.parse(dateBorn)
        val sdf = SimpleDateFormat("dd-MMM-yyyy")

        assertEquals("12-Aug-1990", sdf.format(date))
    }

    @Test
    fun ageConversionTest() {
        val dateBorn = "1990-08-12"
        val today = Calendar.getInstance().time
        val todaySdf = SimpleDateFormat("yyyy")
        val yearBorn = todaySdf.parse(dateBorn)
        val yearToday = todaySdf.format(today)
        val yearBornResult = todaySdf.format(yearBorn)
        val ageResult = yearToday.toInt() - yearBornResult.toInt()
        val ageExpected = 29
        assertEquals(ageExpected, ageResult)
    }
}