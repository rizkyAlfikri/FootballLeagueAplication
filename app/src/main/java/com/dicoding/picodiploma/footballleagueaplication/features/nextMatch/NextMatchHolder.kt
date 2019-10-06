package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_match.view.*

class NextMatchHolder(
    private val nextMatchItem: NextMatchItem,
    private val badgeHome: String,
    private val badgeAway: String,
    private val listStadium: String?,
    private val listener: (NextMatchItem) -> Unit
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            nextMatchItem.let {
                txt_home.text = it.strHomeTeam
                txt_away.text = it.strAwayTeam
                txt_score_home.text = it.intHomeScore?.toString() ?: "-"
                txt_score_away.text = it.intAwayScore?.toString() ?: "-"
                txt_time.text = timeGMTFormat(it.strTime) ?: "-"
                txt_stadium.text = listStadium
            }

            Glide.with(context).load(badgeHome).apply(RequestOptions()).into(img_home)
            Glide.with(context).load(badgeAway).apply(RequestOptions()).into(img_away)

            setOnClickListener { listener(nextMatchItem) }
        }
    }

    override fun getLayout(): Int = R.layout.item_match
}