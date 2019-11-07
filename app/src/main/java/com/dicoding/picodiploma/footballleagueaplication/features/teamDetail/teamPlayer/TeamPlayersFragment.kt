package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.playerdetail.PlayerDetailActivity
import com.dicoding.picodiploma.footballleagueaplication.features.playerdetail.PlayerDetailActivity.Companion.EXTRA_PLAYER
import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerItem
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamPlayerRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.TeamPlayerHolder
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.TeamPlayerTitleHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_team_players.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class TeamPlayersFragment : Fragment(), TeamPlayerView {

    private val playerAdapter = GroupAdapter<ViewHolder>()
    private var playerPresenter: TeamPlayerPresenter? = null

    companion object {
        fun newInstance(idTeam: String): TeamPlayersFragment {
            val fragment = TeamPlayersFragment()
            fragment.arguments = Bundle().apply {
                putString("id", idTeam)
            }
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val idTeam = arguments?.getString("id")
        rv_player.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = playerAdapter
        }

        EspressoIdlingResource.incrementIdle()

        playerPresenter = TeamPlayerPresenter(this, TeamPlayerRepository())
        playerPresenter?.getPlayerFromServer(idTeam)


    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadPlayerToView(dataFormation: Set<String>, dataPlayer: List<PlayerItem>) {
        playerAdapter.clear()

        dataFormation.map { itPosition ->
            playerAdapter.add(
                TeamPlayerTitleHolder(
                    itPosition
                )
            )

            dataPlayer.map { itPlayer ->
                if (itPosition == itPlayer.strPosition) {
                    playerAdapter.add(
                        TeamPlayerHolder(
                            itPlayer
                        ) {
                            startActivity<PlayerDetailActivity>(
                                EXTRA_PLAYER to it.idPlayer
                            )
                        }
                    )
                }
            }
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
        playerPresenter = null
    }
}
