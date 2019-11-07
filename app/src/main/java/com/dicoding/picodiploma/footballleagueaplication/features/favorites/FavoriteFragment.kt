package com.dicoding.picodiploma.footballleagueaplication.features.favorites


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.adapters.FavoritePagerAdapter
import com.google.android.material.tabs.TabLayout

class FavoriteFragment : Fragment() {


    companion object{
        fun newInstance(idLeague: String?): FavoriteFragment {
            val fragment = FavoriteFragment()
            fragment.arguments = Bundle().apply {
                putString("id", idLeague)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idLeague = arguments?.getString("id")

        val favoritePagerAdapter =
            idLeague?.let { FavoritePagerAdapter(requireContext(), it, childFragmentManager) }

        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = favoritePagerAdapter
        val tab: TabLayout = view.findViewById(R.id.tab_layout)
        tab.setupWithViewPager(viewPager)
    }

}
