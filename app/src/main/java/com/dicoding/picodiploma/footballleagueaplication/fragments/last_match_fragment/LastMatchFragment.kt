package com.dicoding.picodiploma.footballleagueaplication.fragments.last_match_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.DetailMatchActivity

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.adapters.LastMatchAdapter
import com.dicoding.picodiploma.footballleagueaplication.models.last.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class LastMatchFragment(private val idLeague: String): Fragment(), LastMatchView {

    private lateinit var lastAdapter: LastMatchAdapter
    private lateinit var lastPresenter: LastMatchPresenter
    private var listData: MutableList<LastMatchItem> = mutableListOf()
    private var listBadgeHome: MutableList<String> = mutableListOf()
    private var listBadgeAway: MutableList<String> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lastPresenter = LastMatchPresenter(this, ApiRepository(), Gson())
        lastPresenter.getLastMatchData(idLeague)


        lastAdapter = LastMatchAdapter(listData, listBadgeHome, listBadgeAway){
            startActivity<DetailMatchActivity>()
        }
        rv_last_match.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = lastAdapter
        }

    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadLastMatch(data: List<LastMatchItem>) {
        listData.clear()
        listData.addAll(data)
    }

    override fun loadHomeBadge(dataHome: MutableList<String>) {
        listBadgeHome.clear()
        listBadgeHome.addAll (dataHome)
    }

    override fun loadAwayBadge(dataAway: MutableList<String>) {
        listBadgeAway.clear()
        listBadgeAway.addAll(dataAway)
    }

    override fun onFailure(throwable: Throwable) {
        toast("$throwable")
    }
}





