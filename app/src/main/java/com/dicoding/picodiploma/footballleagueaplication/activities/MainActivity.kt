package com.dicoding.picodiploma.footballleagueaplication.activities

import android.graphics.Color.WHITE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.footballleagueaplication.MainLeagueActivity
import com.dicoding.picodiploma.footballleagueaplication.MainLeagueActivity.Companion.EXTRA_ID_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.R.array.*
import com.dicoding.picodiploma.footballleagueaplication.adapters.LeagueAdapter
import com.dicoding.picodiploma.footballleagueaplication.models.LeagueModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import org.jetbrains.anko.*
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView


class MainActivity : AppCompatActivity() {

    private var leagueItem: MutableList<LeagueModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        initData()

        // initialize adapter
        val rvLeague = findViewById<RecyclerView>(R.id.rv_legue)
        rvLeague.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
            adapter = LeagueAdapter(leagueItem) {

                /* take action every time the view is clicked on each view
                   put data to EXTRA_LEAGUE and move current view to detail activity
                */
                toast(it.leagueName)
                startActivity<MainLeagueActivity>(EXTRA_ID_LEAGUE to it)
            }
        }
    }

    private fun initData() {
        // get data array from resource string.xml
        val leagueName = resources.getStringArray(league_name)
        val leagueDesc = resources.getStringArray(league_desc)
        val leagueId = resources.getStringArray(league_id)
        val leagueImage = resources.obtainTypedArray(league_image)

        // put data resource to mutable list
        leagueItem.clear()
        for (data in leagueName.indices) {
            leagueItem.add(
                LeagueModel(
                    leagueName[data],
                    leagueDesc[data],
                    leagueId[data],
                    leagueImage.getResourceId(data, 0)
                )
            )
        }
        leagueImage.recycle()
    }

    //  creating main activity layout with anko
    class MainActivityUI : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            scrollView {
                backgroundResource = R.drawable.bgapps
                coordinatorLayout {
                    themedAppBarLayout(R.style.AppTheme) {
                        collapsingToolbarLayout {
                            fitsSystemWindows = true
                            toolbar {
                                id = R.id.toolbar1
                                background = null
                                setTitleTextColor(WHITE)
                                titleResource =
                                    R.string.footbal_league
                                elevation = dip(0).toFloat()
                            }.lparams(width = matchParent) {
                                collapseMode = COLLAPSE_MODE_PIN
                            }
                        }.lparams(width = matchParent, height = matchParent) {
                            scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_SNAP
                        }
                    }.lparams(width = matchParent)
                    frameLayout {
                        recyclerView {
                            id = R.id.rv_legue
                            clipToPadding = false
                        }.lparams(width = matchParent, height = matchParent)
                    }.lparams(width = matchParent, height = matchParent) {
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
                }
            }
        }
    }
}
