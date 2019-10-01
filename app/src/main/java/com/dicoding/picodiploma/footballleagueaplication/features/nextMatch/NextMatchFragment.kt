package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.features.lastMatch.LastMatchDateHolder
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class NextMatchFragment: Fragment(), NextMatchView{

    private var nextAdapter =  GroupAdapter<ViewHolder>()
    private lateinit var nextPresenter: NextMatchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    companion object {
        fun newInstance(idLeague: String): NextMatchFragment {
            val fragment = NextMatchFragment()
            fragment.arguments = Bundle().apply {
                putString("id", idLeague)
            }
            return  fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idLeague = arguments?.getString("id")
        // initialize presenter and run method for get next schedule data from server


        // initialize recycler view
        rv_next_match.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = nextAdapter
        }

        nextPresenter = NextMatchPresenter(this, Gson(), ApiRepository())
        nextPresenter.getNextMatchData(idLeague)
    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }


    override fun loadDataToView(
        dataNextMatch: List<NextMatchItem>,
        dataHome: MutableList<String>,
        dataAway: MutableList<String>,
        dataDate: MutableSet<String>
    ) {

        dataDate.map { itDate ->
            nextAdapter.add(LastMatchDateHolder(itDate))

            for (position in dataNextMatch.indices) {
                if (dataNextMatch[position].dateEvent == itDate) {
                    nextAdapter.add(NextMatchHolder(
                        dataNextMatch[position],
                        dataHome[position],
                        dataAway[position]){

                        startActivity<DetailMatchActivity>( EXTRA_EVENT to it.idEvent)
                    })
                }
            }
        }
    }

    override fun onFailure(throwable: Throwable) {
        // handling error when data failure to display
        toast(throwable.localizedMessage)
    }
}
