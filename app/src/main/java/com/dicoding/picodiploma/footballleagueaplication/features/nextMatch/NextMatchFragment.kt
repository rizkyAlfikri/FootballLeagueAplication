package com.dicoding.picodiploma.footballleagueaplication.features.nextMatch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.repository.NextMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.LastMatchDateHolder
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.NextMatchHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class NextMatchFragment : Fragment(), NextMatchView {

    private var nextAdapter = GroupAdapter<ViewHolder>()
    private var nextPresenter: NextMatchPresenter? = null
    private val listDateEvent = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    companion object {
        fun newInstance(idLeague: String): NextMatchFragment {
            val fragment = NextMatchFragment()
            fragment.arguments = Bundle().apply {
                putString("id", idLeague)
            }
            return fragment
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val idLeague = arguments?.getString("id")
        // initialize presenter and run method for get next schedule data from server

        nextAdapter.clear()

        // initialize recycler view
        rv_next_match.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = nextAdapter
        }

        nextPresenter = NextMatchPresenter(this, NextMatchRepository())
        EspressoIdlingResource.incrementIdle()

        nextPresenter?.getNextMatchData(idLeague)

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadNextMatchData(
        listNextMatch: List<NextMatchItem>,
        listHomeBadge: List<String>,
        listAwayBadge: List<String>,
        listStadium: List<String?>,
        setDate: Set<String>
    ) {

        listDateEvent.clear()
        listDateEvent.addAll(setDate)

        for (positionDate in listDateEvent.indices) {
            nextAdapter.add(LastMatchDateHolder(listDateEvent[positionDate]))

            for (position in listNextMatch.indices) {
                if (listNextMatch[position].dateEvent == listDateEvent[positionDate]) {
                    nextAdapter.add(
                        NextMatchHolder(
                            listNextMatch[position],
                            listHomeBadge[position],
                            listAwayBadge[position],
                            listStadium[position]
                        ) {
                            startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                        })
                }
            }

            nextAdapter.notifyDataSetChanged()
        }

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

    }

    override fun onFailure(throwable: String) {
        // handling error when data failure to display
        toast(throwable)

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nextPresenter = null
    }
}
