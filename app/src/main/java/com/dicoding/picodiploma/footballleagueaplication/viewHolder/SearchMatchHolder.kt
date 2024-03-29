package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.searchMatchModel.SearchMatchItem
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_schedule.view.*

class SearchMatchHolder(
    private val listDataSearch: SearchMatchItem,
    private val listHome: String,
    private val listAway: String,
    private val listStadium: String?,
    private val listener: (SearchMatchItem) -> Unit) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            listDataSearch.apply {
                txt_home.text = strHomeTeam
                txt_away.text = strAwayTeam
                txt_score_home.text = intHomeScore ?: "-"
                txt_score_away.text = intAwayScore ?: "-"
                txt_time.text = timeGMTFormat(strTime)
                txt_date.text = dateGMTFormat(dateEvent)
            }

            txt_stadium.text = listStadium

            Glide.with(context).load(listHome).into(img_home)
            Glide.with(context).load(listAway).into(img_away)

            viewHolder.containerView.setOnClickListener { listener(listDataSearch) }
        }
    }

    override fun getLayout(): Int = R.layout.item_schedule



}