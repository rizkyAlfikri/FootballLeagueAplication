package com.dicoding.picodiploma.footballleagueaplication.activities

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.competition.CompetitionsFragment
import com.dicoding.picodiploma.footballleagueaplication.features.favorites.FavoriteFragment
import com.dicoding.picodiploma.footballleagueaplication.features.home.HomeFragment
import com.dicoding.picodiploma.footballleagueaplication.features.teams.TeamsFragment
import com.dicoding.picodiploma.footballleagueaplication.models.LeagueModel
import com.dicoding.picodiploma.footballleagueaplication.features.searchMatch.SearchMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.searchMatch.SearchMatchActivity.Companion.EXTRA_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.searchMatch.SearchMatchActivity.Companion.EXTRA_LEAGUE_NAME
import com.dicoding.picodiploma.footballleagueaplication.features.searchMatch.SearchMatchActivity.Companion.EXTRA_SEARCH
import kotlinx.android.synthetic.main.activity_main_league.*
import kotlinx.android.synthetic.main.activity_main_league.search_view
import kotlinx.android.synthetic.main.activity_main_league.toolbar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainLeagueActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment

    // key for save intent data from mainActivity
    companion object {
        const val EXTRA_ID_LEAGUE: String = "extra_league"
    }

    private lateinit var leagueModel: LeagueModel

    // initialize fragment in bottom navigation bar
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = HomeFragment.newInstance(leagueModel.leagueId)
                    supportFragmentManager.beginTransaction()
                        .replace(frame_container.id, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_competitions -> {
                    fragment = CompetitionsFragment.newInstance(leagueModel.leagueId)
                    supportFragmentManager.beginTransaction()
                        .replace(frame_container.id, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    fragment = FavoriteFragment.newInstance(leagueModel.leagueId)
                    supportFragmentManager.beginTransaction()
                        .replace(frame_container.id, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_teams -> {
                    fragment = TeamsFragment.newInstance(leagueModel.leagueId, leagueModel.leagueName)
                    supportFragmentManager.beginTransaction()
                        .replace(frame_container.id, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_league -> {
                    startActivity<MainActivity>()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_league)

        // make soft keyboard hide after used
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        // get parceable data from intent
        leagueModel = intent.getParcelableExtra(EXTRA_ID_LEAGUE)

        // initialize bottom navigation bar
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // use home fragment as default fragment in bottom navigation bar
        fragment = HomeFragment.newInstance(leagueModel.leagueId)
        supportFragmentManager.beginTransaction()
            .replace(frame_container.id, fragment, fragment.javaClass.simpleName).commit()

        if (savedInstanceState != null) {
            nav_view.selectedItemId = R.id.navigation_home
        }

        setSupportActionBar(toolbar)
        toolbar.title = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // intialize search view
        val searchManager = getSystemService<SearchManager>()
        search_view.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        search_view.queryHint = getString(R.string.app_name)
        search_view.setIconifiedByDefault(false)
        search_view.isIconified = false
        search_view.clearFocus()
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                // move current activity to search activity where query search as parameter
                startActivity<SearchMatchActivity>(
                    EXTRA_SEARCH to query,
                    EXTRA_LEAGUE to leagueModel.leagueId,
                    EXTRA_LEAGUE_NAME to leagueModel.leagueName
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        }

        search_view.setOnQueryTextListener(queryTextListener)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_setting -> toast("Setting")
        }

        return super.onOptionsItemSelected(item)
    }
}
