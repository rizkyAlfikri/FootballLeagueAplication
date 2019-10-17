package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerItem
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_team_players.*
import org.jetbrains.anko.support.v4.toast


class TeamPlayersFragment : Fragment(), TeamPlayerView {

    private val playerAdapter = GroupAdapter<ViewHolder>()

    companion object{
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

        val playerPresenter = TeamPlayerPresenter(this, Gson(), ApiRepository())
        playerPresenter.getPlayerFromServer(idTeam)
    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadPlayerToView(dataFormation: Set<String>, dataPlayer: List<PlayerItem>) {
        playerAdapter.clear()

        dataFormation.forEach { itPosition ->
            playerAdapter.add(TeamPlayerTitleHolder(itPosition))
            dataPlayer.forEach { itPlayer ->
                if (itPosition == itPlayer.strPosition) {
                    playerAdapter.add(TeamPlayerHolder(itPlayer))
                }
            }
        }
    }

    override fun onFailure(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }


}
