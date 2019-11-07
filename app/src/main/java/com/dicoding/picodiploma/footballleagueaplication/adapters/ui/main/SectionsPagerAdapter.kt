package com.dicoding.picodiploma.footballleagueaplication.adapters.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.lastMatch.LastMatchFragment
import com.dicoding.picodiploma.footballleagueaplication.features.nextMatch.NextMatchFragment
import com.dicoding.picodiploma.footballleagueaplication.features.standingsleague.StandingsLeagueFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_standing,
    R.string.tab_text_1,
    R.string.tab_text_2
)


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val idLeague: String) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // initialize LastFragment and Next Fragment in section pager adapter
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return StandingsLeagueFragment.newInstance(idLeague)
            1 -> return LastMatchFragment.newInstance(idLeague)
            2 -> return NextMatchFragment.newInstance(idLeague)
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    // show fragment according the amount of fragment initialized in this adapter
    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}