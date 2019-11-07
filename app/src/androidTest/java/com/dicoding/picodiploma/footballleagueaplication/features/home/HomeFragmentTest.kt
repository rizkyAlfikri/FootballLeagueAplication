package com.dicoding.picodiploma.footballleagueaplication.features.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.activities.MainActivity
import com.dicoding.picodiploma.footballleagueaplication.networks.RetrofitService
import com.dicoding.picodiploma.footballleagueaplication.utils.CustomScrollActions
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import okreplay.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private val okReplayConfig = OkReplayConfig.Builder()
        .tapeRoot(AndroidTapeRoot(
            InstrumentationRegistry.getInstrumentation().context, javaClass))
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
    @OkReplay(tape = "instrumental home fragment and recycler view behavior", mode = TapeMode.READ_ONLY)
    fun test_behavior_recycler_view_home_fragment() {

        Espresso.onView(ViewMatchers.withId(R.id.rv_legue))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))

        Espresso.onView(ViewMatchers.withId(R.id.rv_league))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        var number = 0
        repeat(7) {
            Espresso.onView(ViewMatchers.withId(R.id.rv_league))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(number))
            number++
        }

        repeat(7) {
            Espresso.onView(ViewMatchers.withId(R.id.rv_league))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(number))
            number--
        }

        Espresso.onView(ViewMatchers.withId(R.id.txt_youtube)).perform(CustomScrollActions.nestedScrollTo()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.rv_league)).perform(CustomScrollActions.nestedScrollTo()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.pressBack()
    }

}