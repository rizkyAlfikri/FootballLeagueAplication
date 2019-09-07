package com.dicoding.picodiploma.footballleagueaplication

import android.graphics.Color.WHITE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.footballleagueaplication.DetailActivity.Companion.EXTRA_LEAGUE
import com.google.android.material.appbar.AppBarLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import com.dicoding.picodiploma.footballleagueaplication.R.array.*
import com.dicoding.picodiploma.footballleagueaplication.models.LeagueModel
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.toolbar


class MainActivity : AppCompatActivity() {

    private var leagueItem: MutableList<LeagueModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        initData()

        val rvLegue = findViewById<RecyclerView>(R.id.rv_legue)
        rvLegue.layoutManager = GridLayoutManager(this, 2)
        rvLegue.setHasFixedSize(true)
        rvLegue.adapter = LeagueAdapter(leagueItem){
            toast(it.leagueName)
            startActivity<DetailActivity>(EXTRA_LEAGUE to it)
        }

    }

    private fun initData() {
        val leagueName = resources.getStringArray(league_name)
        val leagueDesc = resources.getStringArray(league_desc)
        val leagueId = resources.getStringArray(league_id)
        val leagueImage = resources.obtainTypedArray(league_image)

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


    class MainActivityUI : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            scrollView {
                backgroundResource = R.drawable.bgapps
                coordinatorLayout {
                    themedAppBarLayout(R.style.AppTheme) {
                        background = null
                        collapsingToolbarLayout {
                            fitsSystemWindows = true
                            toolbar {
                                id = R.id.toolbar
                                setTitleTextColor(WHITE)
                                popupTheme = R.style.AppTheme
                                title = "Dicoding"
                                elevation = dip(4).toFloat()
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
