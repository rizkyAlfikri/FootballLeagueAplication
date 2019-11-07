package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.repository.SearchMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.SearchMatchHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.progress_bar
import kotlinx.android.synthetic.main.activity_search.rv_search
import kotlinx.android.synthetic.main.activity_search.search_view
import kotlinx.android.synthetic.main.activity_search.toolbar
import kotlinx.android.synthetic.main.activity_search_teams.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private var searchPresenter: SearchMatchPresenter? = null
    private lateinit var queryData: String
    private val searchAdapter = GroupAdapter<ViewHolder>()
    private var idLeague: String? = null
    private var leagueName: String? = null
    private var queryResult: String? = null

    companion object {
        const val EXTRA_SEARCH: String = "extraSearch"
        const val EXTRA_LEAGUE: String = "extraId"
        const val EXTRA_LEAGUE_NAME: String = "extraLeagueName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        idLeague = intent.getStringExtra(EXTRA_LEAGUE)
        leagueName = intent.getStringExtra(EXTRA_LEAGUE_NAME)

        searchAdapter.clear()

        // make soft keyboard hide after used
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        queryData = intent.getStringExtra(EXTRA_SEARCH)
        toast(queryData)
        // initialize presenter and run method for fetch search match data
        searchPresenter = SearchMatchPresenter(this, SearchMatchRepository(idLeague))

        EspressoIdlingResource.incrementIdle()

        searchPresenter?.getSearchMatchData(queryData)


        // initialize recycler view
        rv_search.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
        }

        searchMatchLeague()
    }

    private fun searchMatchLeague() {

        // initialize search view
        val searchManager = getSystemService<SearchManager>()

        search_view.apply {
            setSearchableInfo(searchManager?.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            setQuery(queryData, true)
            isIconified = false
            clearFocus()
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.clear()
                toast("$query")
                EspressoIdlingResource.incrementIdle()
                // run method for get data search from server where query as parameter
                searchPresenter?.getSearchMatchData(query)
                queryResult = query

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

    override fun loadSearchDataToView(
        dataSearch: List<SearchMatchItem>,
        dataHome: MutableList<String>,
        dataAway: MutableList<String>,
        dataStadium: MutableList<String?>
    ) {

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

        searchAdapter.clear()

        for (positions in dataSearch.indices) {
            searchAdapter.add(
                SearchMatchHolder(
                    dataSearch[positions],
                    dataHome[positions],
                    dataAway[positions],
                    dataStadium[positions]
                ) {
                    startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                }
            )
        }

        if (dataSearch.isEmpty()) longToast("Your search $queryResult match did not exits with $leagueName match")

    }

    override fun onFailure(throwable: String) {
        // error handling if data failure to display
        toast(throwable)

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter = null
    }
}
