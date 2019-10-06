package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.adapters.DetailTeamPagerAdapter
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.toast

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    companion object {
        const val EXTRA_TEAM: String = "extraTeam"
        const val EXTRA_LEAGUE: String = "extraLeague"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)


        val idTeam = intent.getStringExtra(EXTRA_TEAM)
        val idLeague = intent.getStringExtra(EXTRA_LEAGUE)


        val teamDetailPresenter = TeamDetailPresenter(this)
        teamDetailPresenter.getTeamDetailData(idTeam)

        val teamPagerAdapter = DetailTeamPagerAdapter(this, idLeague, idTeam, supportFragmentManager)
        view_pager.adapter = teamPagerAdapter
        tab_layout.setupWithViewPager(view_pager)


        collapsing_toolbar.setCollapsedTitleTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
        collapsing_toolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                applicationContext,
                android.R.color.transparent
            )
        )

        var scrollRange = -1

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }

            if (scrollRange + i == 0) {
                tab_layout.tabTextColors = ContextCompat.getColorStateList(applicationContext, android.R.color.white)
            } else {
                tab_layout.tabTextColors = ContextCompat.getColorStateList(applicationContext, R.color.primary_text)
            }
        })



    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadToView(listTeamData: List<TeamDetailItem>) {
        listTeamData.map { itTeam ->
            txt_team.text = itTeam.strTeam
            Glide.with(this).load(itTeam.strTeamBadge).into(img_team)
            collapsing_toolbar.title = itTeam.strTeam


            img_web.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strWebsite}")
                }

                startActivity(intent)
            }

            img_facebook.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strFacebook}")
                }

                startActivity(intent)
            }

            img_twitter.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strTwitter}")
                }

                startActivity(intent)
            }

            img_instagram.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strInstagram}")
                }

                startActivity(intent)
            }

            img_youtube.setOnClickListener {
                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    addCategory(CATEGORY_BROWSABLE)
                    data = Uri.parse("https://${itTeam.strYoutube}")
                }

                startActivity(intent)
            }


        }





    }

    override fun onFailure(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }

}
