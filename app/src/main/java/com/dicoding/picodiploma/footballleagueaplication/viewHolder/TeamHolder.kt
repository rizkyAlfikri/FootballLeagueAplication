package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_team.view.*

class TeamHolder(private val teamBadge: String, private val teamName: String, private val listener: (String) -> Unit) :
    Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_team.text = teamName
            Glide.with(context).load(teamBadge).into(img_team)

            setOnClickListener { listener(teamBadge) }
        }
    }

    override fun getLayout(): Int = R.layout.item_team

}