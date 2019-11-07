package com.dicoding.picodiploma.footballleagueaplication.features.standingsleague


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_TEAM
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.repository.StandingsRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.StandingsLeagueHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_standings.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class StandingsLeagueFragment : Fragment(), StandingsLeagueView {

    private val standingAdapter =  GroupAdapter<ViewHolder>()
    private var standingPresenter: StandingsLeaguePresenter? = null
    private var idLeague: String? = null

    companion object{
        fun newInstance(idLeague: String): Fragment {
            val fragment = StandingsLeagueFragment()
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
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        idLeague = arguments?.getString("id")

        standingAdapter.clear()

        rv_standings.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = standingAdapter
        }

        EspressoIdlingResource.incrementIdle()
        standingPresenter = StandingsLeaguePresenter(this,  StandingsRepository())
        standingPresenter?.getStandingLeague(idLeague)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        standingPresenter = null
    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadTableDataToView(listTable: List<Table>, listBadge: List<String>) {
        for (position in listTable.indices) {
            standingAdapter.add(StandingsLeagueHolder( listTable[position], listBadge[position]){
                startActivity<TeamDetailActivity>(
                    EXTRA_TEAM to it.teamid,
                    EXTRA_LEAGUE to idLeague
                )
            })
        }

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
}
