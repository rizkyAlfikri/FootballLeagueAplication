package com.dicoding.picodiploma.footballleagueaplication.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview.TeamOverviewFragment
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamPlayer.TeamPlayersFragment
import com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamSchedule.TeamScheduleFragment

private val TAB_TITLES = arrayOf(
    R.string.overviews,
    R.string.schedule,
    R.string.Players
)

class DetailTeamPagerAdapter(
    private val context: Context,
    private val idLeague: String,
    private val idTeam: String,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TeamOverviewFragment.newInstance(idLeague, idTeam)
            1 -> TeamScheduleFragment.newInstance(idLeague, idTeam)
            else -> TeamPlayersFragment.newInstance(idTeam)
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}