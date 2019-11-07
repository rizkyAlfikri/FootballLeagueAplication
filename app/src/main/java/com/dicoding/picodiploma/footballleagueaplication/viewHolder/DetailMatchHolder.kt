package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.include_match_detail.view.*

// this class is replacement from RecyclerView Adapter Class
class DetailMatchHolder(private val listMatchDetail: MatchDetailItem) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {

        // load data to  view
        viewHolder.itemView.apply {
            val noData = context.getString(R.string.no_data)
            listMatchDetail.apply {
                txt_formations_home.text = strHomeFormation ?: noData
                txt_formations_away.text = strAwayFormation ?: noData
                txt_shoot_home.text = intHomeShots ?: noData
                txt_shoot_away.text = intAwayShots ?: noData

                txt_red_home.text =
                    if (strHomeRedCards != "") strHomeRedCards?.replace(";", "\n") ?: noData
                    else "0"

                txt_red_away.text =
                    if (strAwayRedCards != "") strAwayRedCards?.replace(";", "\n ") ?: noData
                    else "0"

                txt_yellow_home.text =
                    if (strHomeYellowCards != "") strHomeYellowCards?.replace(";", "\n") ?: noData
                    else "0"

                txt_yellow_away.text =
                    if (strAwayYellowCards != "") strAwayYellowCards?.replace(";", "\n") ?: noData
                    else "0"
            }
        }
    }

    override fun getLayout(): Int = R.layout.include_match_detail
}