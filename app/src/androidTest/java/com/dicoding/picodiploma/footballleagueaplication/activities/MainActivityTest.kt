package com.dicoding.picodiploma.footballleagueaplication.activities

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import okreplay.*
import org.junit.*
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val okReplayConfig = OkReplayConfig.Builder()
        .tapeRoot(
            AndroidTapeRoot(
                InstrumentationRegistry.getInstrumentation().context, javaClass)
        )
        .sslEnabled(true)
        .interceptor(RetrofitService.okReplayInterceptor)
        .build()

    companion object {
        @ClassRule
        @JvmField
        val grantExternalStoragePermissionRule: GrantPermissionRule =
            GrantPermissionRule.grant(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
    }

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val testRule = OkReplayRuleChain(okReplayConfig, activityRule).get()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)

        activityRule.launchActivity(null    )
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }


    @Test
    @OkReplay(tape = "instrumental_test_main_activity_behavior", mode = TapeMode.READ_ONLY)
    fun test_behavior_recycler_view_main_activity() {
        // show main activity with recycler view, scroll down recycler view to position item 8
        onView(withId(R.id.rv_legue)).check(matches(isDisplayed()))
        sleep(1000)
        onView(withId(R.id.rv_legue)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
        sleep(2000)

        // scroll up recycler view to position item 1, and then click item at position 0
        // show main league activity and then back to main activity view
        onView(withId(R.id.rv_legue)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1),
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.rv_league)).check(matches(isDisplayed()))
        sleep(1000)

        pressBack()
        sleep(1000)
    }
}

