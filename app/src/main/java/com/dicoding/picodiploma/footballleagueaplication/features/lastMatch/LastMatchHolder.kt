package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_match.view.*

class LastMatchHolder(
    private val listData: LastMatchItem,
    private val listHome: String,
    private val listAway: String,
    private val listener: (LastMatchItem) -> Unit
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            listData.apply {
                txt_home.text = strHomeTeam
                txt_away.text = strAwayTeam
                txt_score_home.text = intHomeScore.toString()
                txt_score_away.text = intAwayScore.toString()
                txt_time.text = timeGMTFormat(strTime)
                Glide.with(context).load(listHome).apply { RequestOptions() }.into(img_home)
                Glide.with(context).load(listAway).apply { RequestOptions() }.into(img_away)
            }

            setOnClickListener { listener(listData) }
        }
    }

    override fun getLayout(): Int = R.layout.item_match
}