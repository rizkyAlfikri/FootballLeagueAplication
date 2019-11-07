package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.adapters.DetailTeamPagerAdapter
import com.dicoding.picodiploma.footballleagueaplication.db.FavoriteTeamModel
import com.dicoding.picodiploma.footballleagueaplication.db.database
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamDetailRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import java.sql.SQLClientInfoException

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private var teamDetailPresenter: TeamDetailPresenter? = null
    private lateinit var listTableTeam: MutableList<Table>
    private lateinit var listTeamDetail: MutableList<TeamDetailItem>
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var idTeam: String
    private lateinit var idLeague: String

    companion object {
        const val EXTRA_TEAM: String = "extraTeam"
        const val EXTRA_LEAGUE: String = "extraLeague"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        idTeam = intent.getStringExtra(EXTRA_TEAM)
        idLeague = intent.getStringExtra(EXTRA_LEAGUE)

        favoriteState()

        EspressoIdlingResource.incrementIdle()

        teamDetailPresenter = TeamDetailPresenter(this, TeamDetailRepository())
        teamDetailPresenter?.getTeamDetailData(idTeam, idLeague)


        val teamPagerAdapter =
            DetailTeamPagerAdapter(this, idLeague, idTeam, supportFragmentManager)
        view_pager.adapter = teamPagerAdapter
        tab_layout.setupWithViewPager(view_pager)


        collapsing_toolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                android.R.color.white
            )
        )
        collapsing_toolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                applicationContext,
                android.R.color.transparent
            )
        )

        var scrollRange = -1

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }

            if (scrollRange + i == 0) {
                tab_layout.tabTextColors =
                    ContextCompat.getColorStateList(applicationContext, android.R.color.white)
            } else {
                tab_layout.tabTextColors =
                    ContextCompat.getColorStateList(applicationContext, R.color.primary_text)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.action_favorite -> {
                if (this::listTableTeam.isInitialized && this::listTeamDetail.isInitialized) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                    true
                } else {
                    toast("Data still loading please wait")
                    true
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadToView(listTableData: List<Table>, listTeamData: List<TeamDetailItem>) {
        listTeamData.map { itTeam ->
            txt_team.text = itTeam.strTeam
            Glide.with(this).load(itTeam.strTeamBadge).into(img_team)
            collapsing_toolbar.title = itTeam.strTeam


            img_web.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strWebsite}")
                }

                startActivity(intent)
            }

            img_facebook.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strFacebook}")
                }

                startActivity(intent)
            }

            img_twitter.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strTwitter}")
                }

                startActivity(intent)
            }

            img_instagram.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strInstagram}")
                }

                startActivity(intent)
            }

            img_youtube.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strYoutube}")
                }

                startActivity(intent)
            }
        }

        listTableTeam = mutableListOf()
        listTableTeam.clear()
        listTeamDetail = mutableListOf()
        listTeamDetail.clear()

        listTableTeam.addAll(listTableData)
        listTeamDetail.addAll(listTeamData)

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }

    override fun onFailure(throwable: String) {
        toast(throwable)

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        teamDetailPresenter = null
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeamModel.TABLE_TEAM_FAVORITE)
                .whereArgs(
                    "(ID_TEAM = {id})",
                    "id" to idTeam
                )
            val favorite = result.parseList(classParser<FavoriteTeamModel>())
            isFavorite = favorite.isNotEmpty()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteTeamModel.TABLE_TEAM_FAVORITE,
                    FavoriteTeamModel.ID_TEAM to listTeamDetail[0].idTeam,
                    FavoriteTeamModel.ID_LEAGUE to listTeamDetail[0].idLeague,
                    FavoriteTeamModel.TEAM_NAME to listTeamDetail[0].strTeam,
                    FavoriteTeamModel.LEAGUE_NAME to listTeamDetail[0].strLeague,
                    FavoriteTeamModel.TEAM_BADGE to listTeamDetail[0].strTeamBadge,
                    FavoriteTeamModel.WIN_TEAM to listTableTeam[0].win,
                    FavoriteTeamModel.DRAW_TEAM to listTableTeam[0].draw,
                    FavoriteTeamModel.LOST_TEAM to listTableTeam[0].loss
                )
            }
            layout_team.snackbar("Added to favorite")
        } catch (e: SQLClientInfoException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteTeamModel.TABLE_TEAM_FAVORITE, "(ID_TEAM = {id})",
                    "id" to idTeam
                )
            }
            layout_team.snackbar("Removed from favorite")
        } catch (e: SQLClientInfoException) {
            toast(e.localizedMessage)
        }
    }
}
