package com.dicoding.picodiploma.footballleagueaplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatch.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.timeGMTFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_match.*

class NextMatchAdapter(
    private val listData: List<NextMatchItem>,
    private val listHome: MutableList<String>,
    private val listAway: MutableList<String>
): RecyclerView.Adapter<NextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextViewHolder {
        return NextViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match, parent, false)
        )
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: NextViewHolder, position: Int) {
        holder.bindData(listData[position], listHome[position], listAway[position])
    }
}

class NextViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(listData: NextMatchItem?, urlHomeBadge: String, urlAwayBadge: String) {
        listData.let {

            txt_home.text = it?.strHomeTeam ?: "-"
            txt_away.text = it?.strAwayTeam ?: "-"
            txt_score_home.text = it?.intHomeScore?.toString() ?: "-"
            txt_score_away.text = it?.intAwayScore?.toString() ?: "-"
            txt_date.text = dateGMTFormat(it?.dateEvent) ?: "-"
            txt_time.text = timeGMTFormat(it?.strTime) ?: "-"
        }

        Glide.with(itemView).load(urlHomeBadge).apply(RequestOptions()).into(img_home)
        Glide.with(itemView).load(urlAwayBadge).apply(RequestOptions()).into(img_away)
    }
}
