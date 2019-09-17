package com.dicoding.picodiploma.footballleagueaplication.fragments.nextMatchFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.adapters.NextMatchAdapter
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatch.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.toast


class NextMatchFragment(private val idLeague: String) : Fragment(), NextMatchView{

    private var listData: MutableList<NextMatchItem> = mutableListOf()
    private var listHome: MutableList<String> = mutableListOf()
    private var listAway: MutableList<String> = mutableListOf()
    private lateinit var nextAdapter: NextMatchAdapter
    private lateinit var nextPresenter: NextMatchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

//    companion object {
//        fun newInstance(): NextMatchFragment =
//            NextMatchFragment()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextPresenter = NextMatchPresenter(this, Gson(), ApiRepository())
        nextPresenter.getNextMatchData(idLeague)

        nextAdapter = NextMatchAdapter(listData, listHome, listAway)
        rv_next_match.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = nextAdapter
        }

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadDataToView(data: List<NextMatchItem>) {
        listData.clear()
        listData.addAll(data)
    }

    override fun onFailure(throwable: Throwable) {
        toast("$throwable")
    }

    override fun loadHomeBadge(dataHome: MutableList<String>) {
        listHome.clear()
        listHome.addAll(dataHome)
    }

    override fun loadAwayBadge(dataAway: MutableList<String>) {
        listAway.clear()
        listAway.addAll(dataAway)
    }


}
