package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.db.FavoriteTeamModel
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.favorite_team_holde.view.*

class FavoriteTeamHolder(
    private val favorite: FavoriteTeamModel,
    private val listener: (FavoriteTeamModel) -> Unit) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_team.text = favorite.teamName
            txt_league.text = favorite.leagueName
            txt_win.text = "${favorite.winTeam}"
            txt_draw.text = "${favorite.drawTeam}"
            txt_lose.text = "${favorite.lostTeam}"

            Glide.with(context).load(favorite.teamBadge)
                .apply(RequestOptions().override(100, 100))
                .into(img_team)

            setOnClickListener { listener(favorite) }
        }
    }

    override fun getLayout(): Int = R.layout.favorite_team_holde
}