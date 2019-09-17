package com.dicoding.picodiploma.footballleagueaplication.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.LeagueModel
import kotlinx.android.extensions.LayoutContainer


import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class LeagueAdapter(private val listLeague: List<LeagueModel>,
                    private val listener: (LeagueModel) -> Unit ) :
    RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(
            LeagueUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount() = listLeague.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindData(listLeague[position], listener)
    }

    class LeagueViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val txtName = containerView.findViewById<TextView>(R.id.txt_name)
        private val imgPhoto = containerView.findViewById<ImageView>(R.id.img_photo)

        // load data to view
        fun bindData(listData: LeagueModel, listener: (LeagueModel) -> Unit) {
            txtName.text = listData.leagueName
            Glide.with(itemView).load(listData.leagueImage).into(imgPhoto)
            containerView.setOnClickListener { listener(listData) }
        }
    }

    // creating adapter layout with anko
    class LeagueUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            frameLayout {
                cardView {
                    background.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                    radius = dip(12).toFloat()

                    relativeLayout {
                        imageView {
                            id = R.id.img_photo
                        }.lparams(width = 300, height = 300) {
                            centerHorizontally()
                            topMargin = dip(16)
                        }
                        textView {
                            textSize = sp(8).toFloat()
                            textColorResource = R.color.primary_text
                            id = R.id.txt_name
                            textAlignment = TEXT_ALIGNMENT_CENTER
                        }.lparams(width = matchParent, height = wrapContent) {
                            below(R.id.img_photo)
                            horizontalMargin = dip(8)
                            topMargin = dip(8)
                            bottomMargin = dip(16)
                            centerInParent()
                        }
                    }.lparams(width = matchParent, height = matchParent)

                }.lparams(width = matchParent, height = matchParent) {
                    margin = dip(12)
                    elevation = dip(6).toFloat()
                }
            }
        }
    }
}


