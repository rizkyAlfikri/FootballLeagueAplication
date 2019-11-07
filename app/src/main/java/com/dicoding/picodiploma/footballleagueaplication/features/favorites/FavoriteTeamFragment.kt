package com.dicoding.picodiploma.footballleagueaplication.features.favorites


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.db.FavoriteTeamModel
import com.dicoding.picodiploma.footballleagueaplication.db.database
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_LEAGUE
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.TeamDetailActivity.Companion.EXTRA_TEAM
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.FavoriteTeamHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity


class FavoriteTeamFragment : Fragment() {

    private val favoriteTeamAdapter = GroupAdapter<ViewHolder>()
    private var idLeague: String? = null

    companion object {
        fun newInstance(idLeague: String?): FavoriteTeamFragment {
            val fragment = FavoriteTeamFragment()
            fragment.arguments = Bundle()
                .apply {
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
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        idLeague = arguments?.getString("id")

        rv_team.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteTeamAdapter
        }


        showFavorite()
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {

        favoriteTeamAdapter.clear()
        context?.database?.use {
            val result = select(FavoriteTeamModel.TABLE_TEAM_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteTeamModel>())

            favorite
                .filter { it.idLeague == idLeague }
                .map { itFavorite ->
                    favoriteTeamAdapter.add(FavoriteTeamHolder(itFavorite){
                        startActivity<TeamDetailActivity>(
                            EXTRA_TEAM to it.idTeam,
                            EXTRA_LEAGUE to it.idLeague
                        )
                    })
                }
        }
    }
}



