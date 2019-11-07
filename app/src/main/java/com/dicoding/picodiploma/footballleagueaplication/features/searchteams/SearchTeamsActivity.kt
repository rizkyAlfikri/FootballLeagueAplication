package com.dicoding.picodiploma.footballleagueaplication.features.searchteams

import android.app.SearchManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_TEAM
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamItem
import com.dicoding.picodiploma.footballleagueaplication.repository.SearchTeamRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.SearchTeamHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_search_teams.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchTeamsActivity : AppCompatActivity(), SearchTeamView {

    private val searchTeamAdapter = GroupAdapter<ViewHolder>()
    private var searchTeamPresenter: SearchTeamPresenter? = null
    private var queryData: String? = null
    private var idLeague: String? = null
    private var nameLeague: String? = null

    companion object{
        const val EXTRA_ID_LEAGUE: String = "idLeague"
        const val EXTRA_NAME_LEAGUE: String = "nameLeague"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_teams)

        progress_bar.visible()

        idLeague = intent.getStringExtra(EXTRA_ID_LEAGUE)
        nameLeague = intent.getStringExtra(EXTRA_NAME_LEAGUE)

        searchTeamAdapter.clear()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        rv_search.apply {
            layoutManager = LinearLayoutManager(this@SearchTeamsActivity)
            setHasFixedSize(true)
            adapter = searchTeamAdapter
        }

        searchTeamPresenter = SearchTeamPresenter(this, SearchTeamRepository())

        searchTeam()
    }

    private fun searchTeam() {

        val searchManager = getSystemService<SearchManager>()

        search_view.apply {
            setSearchableInfo((searchManager?.getSearchableInfo(componentName)))
            search_view.onActionViewExpanded()
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchTeamAdapter.clear()
                queryData = query
                toast("$query")
                EspressoIdlingResource.incrementIdle()
                searchTeamPresenter?.getSearchTeam(query, idLeague)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_setting -> toast("setting")
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun loadDataToView(dataTable: List<Table>, dataSearchTeam: List<SearchTeamItem>) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

        searchTeamAdapter.clear()
        for (position in dataSearchTeam.indices) {
            searchTeamAdapter.add(SearchTeamHolder(dataTable[position], dataSearchTeam[position]){
                startActivity<TeamDetailActivity>(
                    EXTRA_TEAM to it.idTeam,
                    EXTRA_LEAGUE to it.idLeague
                )
            })
        }

        if (dataSearchTeam.isNullOrEmpty()) {
            longToast("Your search $queryData team is not exist in $nameLeague" )
        }
    }

    override fun onFailure(throwable: String) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

        toast(throwable)
    }
}
