package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailItem
import com.dicoding.picodiploma.footballleagueaplication.utils.datePlayerInfoFormat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_player_info.view.*


class PlayerDetailInfoHolder(private val playerItem: PlayerDetailItem) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            playerItem.apply {
                txt_birth.text = strBirthLocation ?: "-"
                txt_nationality.text = strNationality ?: "-"
                txt_national_team.text = strTeamNational?.toString() ?: "-"
                txt_league_team.text = strTeam ?: "-"
                txt_date_birth.text =
                    if (!dateBorn.isNullOrBlank()) datePlayerInfoFormat(dateBorn) ?: "-" else " - "
                txt_date_debut.text =
                    if (!dateSigned.isNullOrBlank()) datePlayerInfoFormat(dateBorn)
                        ?: "-" else " - "
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_player_info
}