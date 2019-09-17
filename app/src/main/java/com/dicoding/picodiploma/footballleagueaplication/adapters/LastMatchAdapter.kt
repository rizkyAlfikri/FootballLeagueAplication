package com.dicoding.picodiploma.footballleagueaplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.last.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetail.Team
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_match.*

class LastMatchAdapter(
    private val listData: List<LastMatchItem>,
    private val listHome: MutableList<String>,
    private val listAway: MutableList<String>) : RecyclerView.Adapter<LastViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return LastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: LastViewHolder, position: Int) {
        holder.bindData(listData[position], listHome[position], listAway[position])
    }

}

class LastViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(listData: LastMatchItem, urlBadgeHome: String, urlBadgeAway: String) {
        listData.apply {
            txt_home.text = strHomeTeam
            txt_away.text = strAwayTeam
            txt_score_home.text = intHomeScore.toString()
            txt_score_away.text = intAwayScore.toString()
            txt_date.text = dateGMTFormat(dateEvent)
            txt_time.text = timeGMTFormat(strTime)
        }


        Glide.with(itemView).load(urlBadgeHome).apply { RequestOptions() }.into(img_home)
        Glide.with(itemView).load(urlBadgeAway).apply { RequestOptions() }.into(img_away)


    }
}
