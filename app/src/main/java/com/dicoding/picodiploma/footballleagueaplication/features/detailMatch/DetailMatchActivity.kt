package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.db.FavoritesModel
import com.dicoding.picodiploma.footballleagueaplication.db.database
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_TEAM
import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_detail_match2.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.sql.SQLClientInfoException

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private var menuItem: Menu? = null
    private var isFavorite = false
    private lateinit var listTeam: MutableList<MatchDetailItem>
    private lateinit var urlBadgeHome: String
    private lateinit var urlBadgeAway: String
    private lateinit var idEvent: String
    // use Groupie library to replace recycler adapter
    private var detailMatchAdapter = GroupAdapter<ViewHolder>()
    private lateinit var detailMatchPresenter: DetailMatchPresenter

    companion object {
        const val EXTRA_EVENT: String = "idEvent"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match2)
        val intent = intent
        idEvent = intent.getStringExtra(EXTRA_EVENT)
        favoriteState()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // initialize recyvle view
        rv_detail_match.apply {
            layoutManager = LinearLayoutManager(this@DetailMatchActivity)
            setHasFixedSize(true)
            adapter = detailMatchAdapter
        }


        // initialize presenter and run method for fetch data from server
        detailMatchPresenter = DetailMatchPresenter(this, Gson(), ApiRepository())
        detailMatchPresenter.getDetailMatchData(idEvent)


        collapsing_toolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))

    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun loadDataToView(listData: List<MatchDetailItem>) {
        // using multiple view with groupie
        // load data to first view holder class
        listData.map {
            detailMatchAdapter.add(DetailMatchHolder(it))
        }

        // load data to second view holder class
        Section().apply {
            listData.map {
                add(DetailMatchTeamHolder(it))
                detailMatchAdapter.add(this)
            }
        }

        // load data to view
        listData[0].apply {
            txt_home.text = strHomeTeam
            txt_away.text = strAwayTeam
            txt_date.text = dateGMTFormat(dateEvent)
            txt_time.text = timeGMTFormat(strTime)
            txt_score_home.text = intHomeScore ?: "-"
            txt_score_away.text = intAwayScore ?: "-"
            txt_goal_home.text = strHomeGoalDetails?.replace(oldValue = ";", newValue = "\n") ?: ""
            txt_goal_away.text = strAwayGoalDetails?.replace(oldValue = ";", newValue = "\n") ?: ""
        }

        listTeam = mutableListOf()
        listTeam.clear()
        listTeam.addAll(listData)


        img_home.setOnClickListener {
            startActivity<TeamDetailActivity>(
                EXTRA_TEAM to listData[0].idHomeTeam,
                EXTRA_LEAGUE to listData[0].idLeague
            )
        }

        img_away.setOnClickListener {
            startActivity<TeamDetailActivity>(
                EXTRA_TEAM to listData[0].idAwayTeam,
                EXTRA_LEAGUE to listData[0].idLeague
            )
        }

        collapsing_toolbar.title = "${listData[0].strHomeTeam} VS ${listData[0].strAwayTeam}"
    }

    override fun loadHomeBadgeToView(urlHomeBadge: String) {
        // load image badge team home to view
        Glide.with(this).load(urlHomeBadge).into(img_home)


        urlBadgeHome = urlHomeBadge
    }

    override fun loadAwayBadgeToView(urlAwayBadge: String) {
        // load image badge team away to view
        Glide.with(this).load(urlAwayBadge).into(img_away)

        urlBadgeAway = urlAwayBadge
    }

    override fun onFailure(throwable: Throwable) {
        // handling error when data failure to show
        toast(throwable.localizedMessage)
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
                if (this::listTeam.isInitialized) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                    true
                } else {
                    toast("Data still loading, please wait")
                    true
                }

            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    // add data and put insert them to favorite table
    private fun addToFavorite() {
        try {
            listTeam[0].apply {
                database.use {
                    insert(
                        FavoritesModel.TABLE_FAVORITE,
                        FavoritesModel.ID_EVENT to idEvent,
                        FavoritesModel.DATE to dateEvent,
                        FavoritesModel.TIME to strTime,
                        FavoritesModel.HOME_TEAM to strHomeTeam,
                        FavoritesModel.AWAY_TEAM to strAwayTeam,
                        FavoritesModel.HOME_SCORE to intHomeScore,
                        FavoritesModel.AWAY_SCORE to intAwayScore,
                        FavoritesModel.HOME_BAGDE to urlBadgeHome,
                        FavoritesModel.AWAY_BADGE to urlBadgeAway
                    )
                }
            }
        } catch (e: SQLClientInfoException) {
            toast(e.localizedMessage).show()
        }
    }

    // remove data from table favorite
    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoritesModel.TABLE_FAVORITE, "(ID_EVENT = {id})",
                    "id" to idEvent
                )
            }
        } catch (e: SQLClientInfoException) {
            toast(e.localizedMessage)
        }
    }

    // This method is to ensure whether the data in the favorite table exists or not yet
    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

    // this method for me take data from favorite table
    private fun favoriteState() {
        database.use {
            val result = select(FavoritesModel.TABLE_FAVORITE)
                .whereArgs(
                    "(ID_EVENT = {id})",
                    "id" to idEvent
                )
            val favorite = result.parseList(classParser<FavoritesModel>())
            isFavorite = favorite.isNotEmpty()
        }
    }
}
