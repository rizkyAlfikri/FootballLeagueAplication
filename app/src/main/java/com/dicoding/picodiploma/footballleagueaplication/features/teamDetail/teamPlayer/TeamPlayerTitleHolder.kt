package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer

import com.dicoding.picodiploma.footballleagueaplication.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_title.view.*

class TeamPlayerTitleHolder(private val listTitle: String): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_title.text = listTitle
        }
    }

    override fun getLayout(): Int = R.layout.item_title
}