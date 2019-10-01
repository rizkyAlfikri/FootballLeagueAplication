package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class LastMatchFragment: Fragment(), LastMatchView {

    private var lastAdapter = GroupAdapter<ViewHolder>()
    private lateinit var lastPresenter: LastMatchPresenter


    companion object{
        fun newInstance(idLeague: String): LastMatchFragment {
            val fragment = LastMatchFragment()
            fragment.arguments = Bundle().apply {
                putString("id", idLeague)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idLeague = arguments?.getString("id")

        // initialize presenter and run method for fetch past schedule data  from server
        lastPresenter = LastMatchPresenter(this, ApiRepository(), Gson())
        lastPresenter.getLastMatchData(idLeague)

//        lastAdapter = LastMatchAdapter(listData, listBadgeHome, listBadgeAway){
//            startActivity<DetailMatchActivity>(
//                EXTRA_EVENT to it.idEvent)
//        }

        // initialize recycler view
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

    override fun loadLastMatch(
        data: List<LastMatchItem>,
        dataHome: MutableList<String>,
        dataAway: MutableList<String>,
        dataDate: Set<String>
    ) {

        dataDate.forEach { itDate ->
            lastAdapter.add(LastMatchDateHolder(itDate))
            for (position in data.indices) {
                if (data[position].dateEvent == itDate) {
                    lastAdapter.add(LastMatchHolder(data[position], dataHome[position], dataAway[position]){
                        startActivity<DetailMatchActivity>(
                            EXTRA_EVENT to it.idEvent)

                    })
                }
            }
        }


    }



    override fun onFailure(throwable: Throwable) {
        // handling error when data failure to display
        toast("$throwable")
    }
}





