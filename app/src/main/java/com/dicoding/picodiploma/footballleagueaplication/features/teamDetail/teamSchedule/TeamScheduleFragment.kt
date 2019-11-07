package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamScheduleRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.ScheduleHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_team_schedule.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class TeamScheduleFragment : Fragment(), TeamScheduleView {

    private val scheduleAdapter = GroupAdapter<ViewHolder>()
    private var schedulePresenter: TeamSchedulePresenter? = null

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

        EspressoIdlingResource.incrementIdle()
        schedulePresenter = TeamSchedulePresenter(this, TeamScheduleRepository())
        schedulePresenter?.getLastSchedule(idLeague, idTeam)
        schedulePresenter?.getNextSchedule(idLeague, idTeam)

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadLastSchedule(
        listLastMatch: MutableList<LastMatchItem>,
        listLastBadgeHome: MutableList<String>,
        listLastBadgeAway: MutableList<String>,
        listLastStadium: MutableList<String?>
    ) {

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

        for (position in listLastMatch.indices) {

            scheduleAdapter.add(
                ScheduleHolder(
                    listLastMatch[position],
                    listLastBadgeHome[position],
                    listLastBadgeAway[position],
                    listLastStadium[position]
                ) {
                    it as LastMatchItem
                    startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                })
        }
    }


    override fun loadNextSchedule(
        listNextMatch: MutableList<NextMatchItem>,
        listNextBadgeHome: MutableList<String>,
        listNextBadgeAway: MutableList<String>,
        listNextStadium: MutableList<String?>
    ) {

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

        for (position in listNextMatch.indices) {

            scheduleAdapter.add(
                ScheduleHolder(
                    listNextMatch[position],
                    listNextBadgeHome[position],
                    listNextBadgeAway[position],
                    listNextStadium[position]
                ) {
                    it as NextMatchItem
                    startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                })
        }
    }

    override fun onFailure(throwable: String) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

        toast(throwable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        schedulePresenter = null
    }
}
