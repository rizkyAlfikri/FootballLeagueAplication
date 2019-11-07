package com.dicoding.picodiploma.footballleagueaplication.features.teams


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.searchteams.SearchTeamsActivity
import com.dicoding.picodiploma.footballleagueaplication.features.searchteams.SearchTeamsActivity.Companion.EXTRA_ID_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.searchteams.SearchTeamsActivity.Companion.EXTRA_NAME_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_TEAM
import com.dicoding.picodiploma.footballleagueaplication.models.teamModel.TeamResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.TeamHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class TeamsFragment : Fragment(), TeamView {

    private val teamAdapter = GroupAdapter<ViewHolder>()
    private var idLeague: String? = null
    private var nameLeague: String? = null
    private var teamPresenter: TeamPresenter? = null

    companion object {
        fun newInstance(idLeague: String, nameLeague: String): TeamsFragment {
            val fragment = TeamsFragment()
            fragment.arguments = Bundle().apply {
                putString("id", idLeague)
                putString("name", nameLeague )
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
        nameLeague = arguments?.getString("name")
        idLeague = arguments?.getString("id")

        rv_team.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = teamAdapter
        }


        EspressoIdlingResource.incrementIdle()

        teamPresenter = TeamPresenter(this, TeamRepository())
        teamPresenter?.getTeamData(idLeague)

        img_search.setOnClickListener {
            startActivity<SearchTeamsActivity>(
                EXTRA_ID_LEAGUE to idLeague,
                EXTRA_NAME_LEAGUE to nameLeague
            )
        }
    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadDataToView(
        teamResponse: TeamResponse
    ) {

        teamResponse.teams.map { teamItem ->
            teamAdapter.add(
                TeamHolder(
                    teamItem.strTeamBadge,
                    teamItem.strTeam
                ) {
                    startActivity<TeamDetailActivity>(
                        EXTRA_TEAM to teamItem.idTeam,
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

    override fun onDestroyView() {
        super.onDestroyView()
        teamPresenter = null
    }

}
