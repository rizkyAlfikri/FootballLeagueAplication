package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_match_date.view.*

class LastMatchDateHolder(private val date: String): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_date.text = dateGMTFormat(date)
        }
    }

    override fun getLayout(): Int = R.layout.item_match_date
}