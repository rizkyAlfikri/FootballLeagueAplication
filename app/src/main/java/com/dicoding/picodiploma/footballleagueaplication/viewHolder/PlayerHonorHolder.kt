package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_player_honour.view.*

class PlayerHonorHolder(private val playerHonorsItem: PlayerHonorsItem): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_season.text = playerHonorsItem.strSeason ?: " - "
            txt_honour.text = playerHonorsItem.strHonour ?: " - "
            txt_team.text = playerHonorsItem.strTeam ?: " - "
        }
    }

    override fun getLayout(): Int = R.layout.item_player_honour
}