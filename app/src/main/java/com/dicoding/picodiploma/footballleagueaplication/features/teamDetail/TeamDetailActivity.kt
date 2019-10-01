package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.adapters.DetailTeamPagerAdapter
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.toast

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    companion object{
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
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun loadToView(listTeamData: List<TeamDetailItem>) {
        listTeamData.map {
            txt_team.text = it.strTeam
            Glide.with(this).load(it.strTeamBadge).into(img_team)
        }

    }

    override fun onFailure(throwable: Throwable) {
        toast(throwable.localizedMessage)
    }

}
