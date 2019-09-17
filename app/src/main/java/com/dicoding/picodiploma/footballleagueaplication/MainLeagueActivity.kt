package com.dicoding.picodiploma.footballleagueaplication

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.footballleagueaplication.fragments.CompetitionsFragment
import com.dicoding.picodiploma.footballleagueaplication.fragments.FavoriteFragment
import com.dicoding.picodiploma.footballleagueaplication.fragments.home_fragment.HomeFragment
import com.dicoding.picodiploma.footballleagueaplication.fragments.TeamsFragment
import com.dicoding.picodiploma.footballleagueaplication.models.LeagueModel
import kotlinx.android.synthetic.main.activity_main_league.*
import org.jetbrains.anko.toast

class MainLeagueActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment

    companion object{
        const val EXTRA_ID_LEAGUE: String = "extra_league"
    }

    private lateinit var leagueModel: LeagueModel

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(frame_container.id, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_competitions -> {
                fragment = CompetitionsFragment(leagueModel.leagueId)
                supportFragmentManager.beginTransaction()
                    .replace(frame_container.id, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                fragment = FavoriteFragment()
                supportFragmentManager.beginTransaction()
                    .replace(frame_container.id, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_teams -> {
                fragment = TeamsFragment()
                supportFragmentManager.beginTransaction()
                    .replace(frame_container.id, fragment, fragment.javaClass.simpleName).commit()
            }
            R.id.navigation_league -> {
                toast(R.string.league)
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_league)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        leagueModel = intent.getParcelableExtra(EXTRA_ID_LEAGUE)

        fragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(frame_container.id, fragment, fragment.javaClass.simpleName).commit()

        if (savedInstanceState != null) {
            nav_view.selectedItemId = R.id.navigation_home
        }
    }
}
