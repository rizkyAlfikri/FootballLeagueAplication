package com.dicoding.picodiploma.footballleagueaplication.features.competition


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.adapters.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout


class CompetitionsFragment : Fragment() {

    companion object {
        fun newInstance(idLeague: String): CompetitionsFragment {
            val fragment = CompetitionsFragment()
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
        return inflater.inflate(R.layout.fragment_competitions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idLeague = arguments?.getString("id")

        // initialize view pager and adapter
        val sectionsPagerAdapter =
            idLeague?.let { SectionsPagerAdapter(requireContext(), childFragmentManager, it) }
        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = view.findViewById(R.id.tab_layout)
        tabs.setupWithViewPager(viewPager)
    }
}
