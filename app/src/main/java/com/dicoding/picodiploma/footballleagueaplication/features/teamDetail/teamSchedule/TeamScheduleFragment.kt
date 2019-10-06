package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_team_schedule.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class TeamScheduleFragment : Fragment(), TeamScheduleView {


    private val scheduleAdapter = GroupAdapter<ViewHolder>()

    companion object {

        fun newInstance(idLeague: String, idTeam: String): TeamScheduleFragment {
            val fragment = TeamScheduleFragment()
            fragment.arguments = Bundle().apply {
                putString("idLeague", idLeague)
                putString("idTeam", idTeam)
            }
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_schedule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val idLeague = arguments?.getString("idLeague")
        val idTeam = arguments?.getString("idTeam")

        rv_schedule.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = scheduleAdapter
        }

        scheduleAdapter.clear()

        val schedulePresenter = TeamSchedulePresenter(this, Gson(), ApiRepository())
        schedulePresenter.getLastMatchData(idLeague, idTeam)
        schedulePresenter.getNextMatchData(idLeague, idTeam)

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadLastTeam(
        listLastMatch: MutableList<LastMatchItem>,
        listLastBadgeHome: MutableList<String>,
        listLastBadgeAway: MutableList<String>,
        listLastStadiumHome: MutableList<String?>
    ) {

        for (position in listLastMatch.indices) {

            scheduleAdapter.add(
                ScheduleHolder(
                    listLastMatch[position],
                    listLastBadgeHome[position],
                    listLastBadgeAway[position],
                    listLastStadiumHome[position]
                ) {
                    it as LastMatchItem
                    startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                })
        }
    }


    override fun loadNextTeam(
        listNextMatch: MutableList<NextMatchItem>,
        listNextBadgeHome: MutableList<String>,
        listNextBadgeAway: MutableList<String>,
        listNextStadiumHome: MutableList<String?>
    ) {

        for (position in listNextMatch.indices) {

            scheduleAdapter.add(
                ScheduleHolder(
                    listNextMatch[position],
                    listNextBadgeHome[position],
                    listNextBadgeAway[position],
                    listNextStadiumHome[position]
                ) {
                    it as NextMatchItem
                    startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                })
        }
    }

    override fun onFailure(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }
}
