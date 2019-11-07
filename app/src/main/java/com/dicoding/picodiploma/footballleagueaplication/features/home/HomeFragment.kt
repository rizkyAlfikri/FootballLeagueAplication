package com.dicoding.picodiploma.footballleagueaplication.features.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetailModel.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.repository.LeagueDetailRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.BannerCarouselHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

class HomeFragment : Fragment(), LeagueDetailView {

    private var homeAdapter = GroupAdapter<ViewHolder>()
    private lateinit var homePresenter: LeagueDetailPresenter

        companion object {
        fun newInstance(idLeague: String): HomeFragment {
            val fragment = HomeFragment()
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

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EspressoIdlingResource.incrementIdle()
        // initialize presenter and run method for fetch data from server
        homePresenter = LeagueDetailPresenter(this, LeagueDetailRepository())
        arguments?.let {
            homePresenter.getLeagueDetail(it.getString("id"))
        }

        // initialize recycler view
        rv_league.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = homeAdapter
        }

        // this staement make our recycle view like similar view pager
        PagerSnapHelper().attachToRecyclerView(rv_league)
        indicator.attachToRecyclerView(rv_league)

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    // load detail league data to view
    override fun loadDataToView(listData: LeagueDetailResponse) {
        listData.leagues[0].apply {
            txt_league?.text = strLeague ?: ""
            txt_formed?.text = intFormedYear
            txt_first_date?.text = dateGMTFormat(dateFirstEvent)
            txt_country?.text = strCountry
            txt_desc?.text = strDescriptionEN
            txt_web?.text = strWebsite
            txt_facebook?.text = strFacebook
            txt_twiter?.text = strTwitter
            txt_youtube?.text = strYoutube

        }
    }

    // load banner image to view
    override fun loadBannerToView(listBanner: MutableList<String>) {
        listBanner.map {
            homeAdapter.add(BannerCarouselHolder(it))
        }

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }

    // handling error when data failure to display
    override fun onFailure(throwable: String) {
        toast(throwable)

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }
}
