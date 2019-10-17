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
import com.dicoding.picodiploma.footballleagueaplication.features.lastMatch.LastMatchDateHolder
import com.dicoding.picodiploma.footballleagueaplication.models.nextMatchModel.NextMatchItem
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class NextMatchFragment : Fragment(), NextMatchView {

    private var nextAdapter = GroupAdapter<ViewHolder>()
    private lateinit var nextPresenter: NextMatchPresenter
    private lateinit var listNextMatch: MutableList<NextMatchItem>
    private val listBadgeHome = mutableListOf<String>()
    private val listBadgeAway = mutableListOf<String>()
    private val listStadium = mutableListOf<String?>()
    private val dateEvent = mutableListOf<String>()

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

        nextPresenter = NextMatchPresenter(this)
        nextPresenter.getNextMatchData(idLeague)

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadHomeTeam(dataHome: MutableList<String>, dataStadium: MutableList<String?>) {
        listBadgeHome.addAll(dataHome)
        listStadium.addAll(dataStadium)

        for (positionData in dateEvent.indices) {
            nextAdapter.add(LastMatchDateHolder(dateEvent[positionData]))

            for (position in dataHome.indices) {
                if (listNextMatch[position].dateEvent == dateEvent[positionData]) {
                    nextAdapter.add(
                        NextMatchHolder(
                            listNextMatch[position],
                            dataHome[position],
                            listBadgeAway[position],
                            listStadium[position]
                        ) {
                            startActivity<DetailMatchActivity>( EXTRA_EVENT to it.idEvent)
                        })
                }
            }
        }
    }

    override fun loadAwayTeam(dataAway: MutableList<String>) {
        listBadgeAway.addAll(dataAway)

    }

    override fun loadDataToView(data: List<NextMatchItem>, dataDate: Set<String>) {
        listNextMatch = mutableListOf()
        listNextMatch.clear()
        listNextMatch.addAll(data)
        dateEvent.addAll(dataDate)


//        dataDate.forEach { itDate ->
//            nextAdapter.add(LastMatchDateHolder(itDate))
//
//            for (position in dataNextMatch.indices) {
//                if (dataNextMatch[position].dateEvent == itDate) {
//                    nextAdapter.add(NextMatchHolder(
//                        dataNextMatch[position],
//                        dataHome[position],
//                        dataAway[position],
//                        dataStadium[position]){
//
//                        startActivity<DetailMatchActivity>( EXTRA_EVENT to it.idEvent)
//                    })
//                }
//            }
//        }
    }

    override fun onFailure(throwable: String) {
        // handling error when data failure to display
        toast(throwable)
    }
}
