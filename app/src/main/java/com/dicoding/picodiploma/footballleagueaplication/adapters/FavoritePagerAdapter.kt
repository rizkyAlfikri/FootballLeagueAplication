package com.dicoding.picodiploma.footballleagueaplication.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.favorites.FavoriteMatchFragment
import com.dicoding.picodiploma.footballleagueaplication.features.favorites.FavoriteTeamFragment

private val TAB_TITLE = arrayOf(
    R.string.match,
    R.string.team
)

class FavoritePagerAdapter(
    private val context: Context,
    private val idLeague: String,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMatchFragment.newInstance(idLeague)
            1 -> FavoriteTeamFragment.newInstance(idLeague)
            else -> FavoriteMatchFragment.newInstance(idLeague)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLE[position])
    }

    override fun getCount(): Int {
        return 2
    }

}