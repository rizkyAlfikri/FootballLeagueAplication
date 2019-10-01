package com.dicoding.picodiploma.footballleagueaplication.features.home

import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_carousel_banner.view.*

// this class for display banner with using recycler view
class BannerCarouselHolder(
    private val listBanner: String): Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            Glide.with(context).load(listBanner).into(img_banner)
        }
    }

    override fun getLayout(): Int = R.layout.item_carousel_banner
}