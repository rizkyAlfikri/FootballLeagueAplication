package com.dicoding.picodiploma.footballleagueaplication.features.searchMatch

import android.app.SearchManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchHolder
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private lateinit var searchPresenter: SearchMatchPresenter
    private lateinit var queryData: String
    private val searchAdapter = GroupAdapter<ViewHolder>()

    companion object {
        const val EXTRA_SEARCH: String = "extraSearch"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // make soft keyboard hide after used
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        queryData = intent.getStringExtra(EXTRA_SEARCH)

        // initialize presenter and run method for fetch search match data
        searchPresenter = SearchMatchPresenter(this, Gson(), ApiRepository())
        searchPresenter.getSearchMatchData(queryData)

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

        search_view.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        search_view.setIconifiedByDefault(false)
        search_view.setQuery(queryData, true)
        search_view.isIconified = false
        search_view.clearFocus()

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                // run method for get data search from server where query as parameter
                searchPresenter.getSearchMatchData(query)
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

    override fun loadDataToView(
        data: MutableList<SearchMatchItem>,
        dataHome: MutableList<String>,
        dataAway: MutableList<String>
    ) {

        // clear data in adapter when adapter will load new data
        searchAdapter.clear()

        // load data to adapter
        for (positions in data.indices) {
            searchAdapter.add(
                SearchMatchHolder(
                    data[positions],
                    dataHome[positions],
                    dataAway[positions]
                ) {
                    startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                }
            )
        }
    }

    override fun onFailure(throwable: Throwable) {
        // error handling if data failure to display
        toast(throwable.localizedMessage)
    }

}
