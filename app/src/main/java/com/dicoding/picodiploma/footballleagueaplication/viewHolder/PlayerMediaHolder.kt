package com.dicoding.picodiploma.footballleagueaplication.viewHolder

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.playerdetail.PlayerDetailActivity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_player_media.view.*
import org.jetbrains.anko.toast

class PlayerMediaHolder(private val activity: PlayerDetailActivity,
    private val namePlayer: String,
    private val urlFacebook: String,
    private val urlTwitter: String,
    private val urlInstagram: String
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {

            txt_media.text = namePlayer

            img_facebook.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://$urlFacebook")
                }

                if (!urlFacebook.isBlank()) {
                    activity.startActivity(intent)
                } else {
                    activity.toast("There is not data for facebook")
                }

            }

            img_twitter.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://$urlTwitter")
                }

                if (!urlFacebook.isBlank()) {
                    activity.startActivity(intent)
                } else {
                    activity.toast("There is not data for twitter")
                }
            }

            img_instagram.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://$urlInstagram")
                }

                if (!urlFacebook.isBlank()) {
                    activity.startActivity(intent)
                } else {
                    activity.toast("There is not data for instagram")
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_player_media
}