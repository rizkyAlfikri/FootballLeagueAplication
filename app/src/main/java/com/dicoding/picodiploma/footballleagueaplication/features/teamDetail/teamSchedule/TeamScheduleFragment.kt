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
import com.dicoding.picodiploma.footballleagueaplication.features.lastMatch.LastMatchDateHolder
import com.dicoding.picodiploma.footballleagueaplication.features.lastMatch.LastMatchHolder
import com.dicoding.picodiploma.footballleagueaplication.features.nextMatch.NextMatchHolder
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
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

        val schedulePresenter = TeamSchedulePresenter(this)
        schedulePresenter.getMatchData(idLeague, idTeam)

    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun getScheduleData(listLastMatch: List<LastMatchItem>, listNextMatchItem: List<NextMatchItem>) {
        listLastMatch.map { item ->
            scheduleAdapter.add(LastMatchDateHolder(item.dateEvent))
            scheduleAdapter.add(LastMatchHolder(item, "test", "test") {
                startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
            })

        }

        listNextMatchItem.map { item ->
            scheduleAdapter.add(LastMatchDateHolder(item.dateEvent))
            scheduleAdapter.add(NextMatchHolder(item, "test", "test"){
                startActivity<DetailMatchActivity>( EXTRA_EVENT to it.idEvent)
            })

        }
    }

    override fun onFailure(throwable: Throwable?) {
        toast("${throwable?.localizedMessage}")
    }
}
