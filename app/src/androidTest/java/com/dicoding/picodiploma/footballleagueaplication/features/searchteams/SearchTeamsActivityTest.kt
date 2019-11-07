package com.dicoding.picodiploma.footballleagueaplication.features.searchteams

import android.widget.AutoCompleteTextView
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
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import okreplay.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTeamsActivityTest {
    private val okReplayConfig = OkReplayConfig.Builder()
        .tapeRoot(
            AndroidTapeRoot(
                InstrumentationRegistry.getInstrumentation().context, javaClass
            )
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

        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    @OkReplay(
        tape = "instrumental_test_search_team_and_add_to_favorite",
        mode = TapeMode.READ_ONLY
    )
    fun test_search_team_and_add_team_to_favorite() {


        // move to main detail league
        Espresso.onView(ViewMatchers.withId(R.id.rv_legue))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.navigation_teams))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.img_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())

        // move to search team activity
        Espresso.onView(ViewMatchers.withId(R.id.search_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(ViewActions.typeText("Man United"), ViewActions.pressImeActionButton())

        Espresso.onView(ViewMatchers.withId(R.id.rv_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rv_search)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        // detail team
        Espresso.onView(ViewMatchers.withId(R.id.action_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Added to favorite"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.pressBack()

        // Search Chelsea Match
        Espresso.onView(ViewMatchers.withId(R.id.search_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(ViewActions.clearText())
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            ViewActions.typeText("Man City"), ViewActions.closeSoftKeyboard()
            , ViewActions.pressImeActionButton()
        )

        Espresso.onView(ViewMatchers.withId(R.id.rv_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rv_search)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        // detail team
        Espresso.onView(ViewMatchers.withId(R.id.action_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Added to favorite"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.pressBack()
        Espresso.pressBack()
        Espresso.pressBack()



    }


}