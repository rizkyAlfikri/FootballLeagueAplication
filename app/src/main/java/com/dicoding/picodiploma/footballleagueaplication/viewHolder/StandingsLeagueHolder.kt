package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.standing_table_item.view.*

class StandingsLeagueHolder(
    private val tableData: Table,
    private val badgeData: String,
    private val listener: (Table) -> Unit
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tableData.apply {
                txt_team.text = name
                txt_played.text = "$played"
                txt_goal_for.text = "$goalsfor"
                txt_goal_against.text = "$goalsagainst"
                txt_goal_difference.text = "$goalsdifference"
                txt_win.text = "$win"
                txt_draw.text = "$draw"
                txt_loss.text = "$loss"
                txt_total.text = "$total"
            }

            Glide.with(context)
                .load(badgeData)
                .apply(RequestOptions().override(24, 24))
                .into(img_team)

            setOnClickListener { listener(tableData) }
        }
    }

    override fun getLayout(): Int = R.layout.standing_table_item
}