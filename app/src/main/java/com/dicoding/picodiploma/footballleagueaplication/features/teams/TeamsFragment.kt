package com.dicoding.picodiploma.footballleagueaplication.features.teams


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_TEAM
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class TeamsFragment : Fragment(), TeamView {

    private val teamAdapter = GroupAdapter<ViewHolder>()
    private var idLeague: String? = null
    companion object {
        fun newInstance(idLeague: String): TeamsFragment {
            val fragment = TeamsFragment()
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
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        idLeague = arguments?.getString("id")

        rv_team.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = teamAdapter
        }

        val teamPresenter = TeamPresenter(this)
        teamPresenter.getTeamData(idLeague)

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadDataToView(
        listBadgeTeam: MutableList<String>,
        listTeamName: MutableList<String>,
        listIdTeam: MutableList<String>
    ) {

        for (position in listBadgeTeam.indices) {
            teamAdapter.add(TeamHolder(listBadgeTeam[position], listTeamName[position]) {
                startActivity<TeamDetailActivity>(EXTRA_TEAM to listIdTeam[position],
                    EXTRA_LEAGUE to idLeague)
            })
        }

    }

    override fun onFailure(throwable: Throwable?) {
        toast("${throwable?.localizedMessage}")
    }

}
