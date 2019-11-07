package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.searchteammodel.SearchTeamItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.favorite_team_holde.view.*

class SearchTeamHolder(
    private val table: Table,
    private val searchTeam: SearchTeamItem,
    private val listener: (SearchTeamItem) -> Unit
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_team.text = searchTeam.strTeam
            txt_league.text = searchTeam.strLeague
            txt_win.text = "${table.win}"
            txt_draw.text = "${table.draw}"
            txt_lose.text = "${table.loss}"

            Glide.with(context).load(searchTeam.strTeamBadge)
                .apply(RequestOptions().override(100, 100))
                .into(img_team)

            setOnClickListener { listener(searchTeam) }

        }
    }

    override fun getLayout(): Int = R.layout.favorite_team_holde


}