package com.dicoding.picodiploma.footballleagueaplication.activities

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dicoding.picodiploma.footballleagueaplication.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testRecyclerView() {
        onView(withId(R.id.rv_legue)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_legue)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
        sleep(5000)
        onView(withId(R.id.rv_legue)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        sleep(5000)
//        onView(withId(R.id.rv_legue)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
//        sleep(2000)
//        onView(withId(R.id.rv_legue)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

    }

}