package com.dicoding.picodiploma.footballleagueaplication.features.detailMatch

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.matchDetailModel.MatchDetailItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.include_match_team.view.*

// this class for replacement from recycler view adapter class
class DetailMatchTeamHolder(private val listMatchDetail: MatchDetailItem): Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            val noData = context.getString(R.string.no_data)

            // load data to view
            listMatchDetail.apply {
                txt_goalkepers_home.text =
                    if (strHomeLineupGoalkeeper != "")
                        strHomeLineupGoalkeeper?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_goalkepers_away.text =
                    if (strAwayLineupGoalkeeper != "" )
                        strAwayLineupGoalkeeper?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_defense_home.text =
                    if (strHomeLineupDefense != "")
                        strHomeLineupDefense?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_defense_away.text =
                    if (strAwayLineupDefense != "")
                        strAwayLineupDefense?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_midfield_home.text =
                    if (strHomeLineupMidfield != "")
                        strHomeLineupMidfield?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_midfield_away.text =
                    if (strAwayLineupMidfield != "")
                        strAwayLineupMidfield?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_forward_home.text =
                    if (strHomeLineupForward != "")
                        strHomeLineupForward?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_forward_away.text = if (strAwayLineupForward != "")
                    strAwayLineupForward?.replace(oldValue = ";", newValue = "\n") ?: noData
                    else noData

                txt_subtitutes_home.text =
                    if (strHomeLineupSubstitutes != "")
                        strHomeLineupSubstitutes?.replace(oldValue = ";", newValue = "\n") ?: noData
                        else noData

                txt_subtitutes_away.text =
                    if (strAwayLineupSubstitutes != "")
                        strAwayLineupSubstitutes?.replace(oldValue = ";", newValue = "\n") ?: noData
                    else noData
            }
        }
    }

    override fun getLayout(): Int = R.layout.include_match_team
}