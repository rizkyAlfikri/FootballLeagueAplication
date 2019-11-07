package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.playerModel.PlayerItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_list_player.view.*

class TeamPlayerHolder(private val player: PlayerItem, private val listener: (PlayerItem) -> Unit): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_player.text = player.strPlayer
            txt_player_numb.text = player.strNumber

            Glide.with(context).load(player.strCutout).into(img_player)

            setOnClickListener { listener(player) }
        }
    }

    override fun getLayout(): Int = R.layout.item_list_player
}