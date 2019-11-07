package com.dicoding.picodiploma.footballleagueaplication.features.lastMatch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.dicoding.picodiploma.footballleagueaplication.models.lastMatchModel.LastMatchItem
import com.dicoding.picodiploma.footballleagueaplication.repository.LastMatchRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.LastMatchDateHolder
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.LastMatchHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_last_match.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class LastMatchFragment : Fragment(), LastMatchView {
    
    private var lastAdapter = GroupAdapter<ViewHolder>()
    private var lastPresenter: LastMatchPresenter? = null
    private val dateEventResult = mutableListOf<String>()

    companion object {
        fun newInstance(idLeague: String): LastMatchFragment {
            val fragment = LastMatchFragment()
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
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idLeague = arguments?.getString("id")
        lastAdapter.clear()
        // initialize presenter and run method for fetch past schedule data  from server
        lastPresenter = LastMatchPresenter(this, LastMatchRepository())

        EspressoIdlingResource.incrementIdle()
        runBlocking {
            val jobStart = GlobalScope.launch(start = CoroutineStart.LAZY) {
                lastPresenter?.getLastMatchData(idLeague)
            }

            jobStart.join()
        }

        // initialize recycler view
        rv_last_match.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = lastAdapter
        }
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadLastMatchData(
        listLastMatch: List<LastMatchItem>,
        listHomeBadge: List<String>,
        listAwayBadge: List<String>,
        listStadium: List<String?>,
        setDate: Set<String>
    ) {

        dateEventResult.clear()
        dateEventResult.addAll(setDate)

        for (positionDate in dateEventResult.indices) {
            lastAdapter.add(LastMatchDateHolder(dateEventResult[positionDate]))

            for (position in listLastMatch.indices) {
                if (listLastMatch[position].dateEvent == dateEventResult[positionDate]) {
                    lastAdapter.add(LastMatchHolder(
                        listLastMatch[position],
                        listHomeBadge[position],
                        listAwayBadge[position],
                        listStadium[position]
                    ) {
                        startActivity<DetailMatchActivity>(
                            EXTRA_EVENT to it.idEvent
                        )
                    })
                }
            }
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
        lastPresenter = null
    }


}






