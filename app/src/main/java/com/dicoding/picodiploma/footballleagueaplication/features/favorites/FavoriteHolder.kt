package com.dicoding.picodiploma.footballleagueaplication.features.favorites

import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.db.FavoritesModel
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_match.view.*

class FavoriteHolder(private val listFavorite: FavoritesModel, private val listener: (FavoritesModel) -> Unit) : Item() {


    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_home.text = listFavorite.homeTeam
            txt_away.text = listFavorite.awayTeam
            txt_score_home.text = listFavorite.homeScore
            txt_score_away.text = listFavorite.awayScore
            txt_time.text = timeGMTFormat(listFavorite.time)
            Glide.with(context).load(listFavorite.homeBadge).into(img_home)
            Glide.with(context).load(listFavorite.awayBadge).into(img_away)

            setOnClickListener { listener(listFavorite) }
        }


    }

    override fun getLayout(): Int = R.layout.item_match
}