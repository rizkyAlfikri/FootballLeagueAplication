package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_match.view.img_away
import kotlinx.android.synthetic.main.item_match.view.img_home
import kotlinx.android.synthetic.main.item_match.view.txt_away
import kotlinx.android.synthetic.main.item_match.view.txt_home
import kotlinx.android.synthetic.main.item_match.view.txt_score_away
import kotlinx.android.synthetic.main.item_match.view.txt_score_home
import kotlinx.android.synthetic.main.item_match.view.txt_time
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleHolder(
    private val listData: Any,
    private val listHome: String,
    private val listAway: String,
    private val listStadium: String?,
    private val listener: (Any) -> Unit
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {

            when (listData) {
                is LastMatchItem -> {
                    listData.apply {
                        txt_home.text = strHomeTeam
                        txt_away.text = strAwayTeam
                        txt_score_home.text = intHomeScore.toString()
                        txt_score_away.text = intAwayScore.toString()
                        txt_date.text = dateGMTFormat(dateEvent)
                        txt_time.text = timeGMTFormat(strTime)
                        txt_stadium.text = listStadium
                        setOnClickListener { listener(this) }
                    }
                }

                is NextMatchItem -> {
                    listData.apply {
                        txt_home.text = strHomeTeam
                        txt_away.text = strAwayTeam
                        txt_score_home.text = intHomeScore?.toString() ?: "-"
                        txt_score_away.text = intAwayScore?.toString() ?: "-"
                        txt_stadium.text = listStadium
                        txt_time.text = timeGMTFormat(strTime) ?: "-"
                        txt_date.text = dateGMTFormat(dateEvent) ?: "-"
                        setOnClickListener { listener(this) }
                    }
                }
            }

            Glide.with(context).load(listHome).apply { RequestOptions() }.into(img_home)
            Glide.with(context).load(listAway).apply { RequestOptions() }.into(img_away)
        }

    }

    override fun getLayout(): Int = R.layout.item_schedule
}