package com.dicoding.picodiploma.footballleagueaplication.fragments.home_fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.items.BannerCarouselItem
import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetail.League
import com.dicoding.picodiploma.footballleagueaplication.models.leagueDetail.LeagueDetailResponse
import com.dicoding.picodiploma.footballleagueaplication.networks.ApiRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.dateGMTFormat
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

class HomeFragment(private val idLeague: String) : Fragment(), LeagueDetailView {

    private var homeAdapter = GroupAdapter<ViewHolder>()
    private lateinit var homePresenter: LeagueDetailPresenter
    private var listLeague: MutableList<League> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homePresenter = LeagueDetailPresenter(this, Gson(), ApiRepository())
        homePresenter.getLeagueDetail(idLeague)
        homeAdapter = GroupAdapter()

        rv_league.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
        PagerSnapHelper().attachToRecyclerView(rv_league)
        indicator.attachToRecyclerView(rv_league)

        toast("listLeague di on view created sizenya = ${listLeague.size}")
    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadDataToView(listData: LeagueDetailResponse) {
        listLeague.clear()
        listLeague.addAll(listData.leagues)
        listData.leagues[0].apply {
            txt_league.text = strLeague
            txt_formed.text = intFormedYear
            txt_first_date.text = dateGMTFormat(dateFirstEvent)
            txt_country.text = strCountry
            txt_desc.text = strDescriptionEN
            txt_web.text = strWebsite
            txt_facebook.text = strFacebook
            txt_twiter.text = strTwitter
            txt_youtube.text = strYoutube
        }
    }

    override fun loadBannerToView(listBanner: MutableList<String>) {
        listBanner.map {
            homeAdapter.add(BannerCarouselItem(it))
        }
    }

    override fun onFailure(throwable: Throwable) {
        toast("$throwable")
    }
}
