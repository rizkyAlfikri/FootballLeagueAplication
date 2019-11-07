package com.dicoding.picodiploma.footballleagueaplication.features.teamDetail.teamOverview


import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.TeamTableModel.Table
import com.dicoding.picodiploma.footballleagueaplication.models.teamDetailModel.TeamDetailItem
import com.dicoding.picodiploma.footballleagueaplication.repository.TeamOverviewRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import kotlinx.android.synthetic.main.fragment_team_overview.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import org.jetbrains.anko.support.v4.toast


class TeamOverviewFragment : Fragment(), TeamOverviewView {

    private val tableList = mutableListOf<SliceValue>()
    private var overviewPresenter: TeamOverviewPresenter? = null

    companion object {
        fun newInstance(idLeague: String, idTeam: String): TeamOverviewFragment {
            val fragment = TeamOverviewFragment()
            fragment.arguments = Bundle().apply {
                putString("idLeague", idLeague)
                putString("idTeam", idTeam)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val idLeague = arguments?.getString("idLeague")
        val idTeam = arguments?.getString("idTeam")


        EspressoIdlingResource.incrementIdle()

        overviewPresenter = TeamOverviewPresenter(this, TeamOverviewRepository())
        overviewPresenter?.getTeamOverviewData(idLeague, idTeam)

    }

    override fun showLoading() {
        progress_bar?.visible()
    }

    override fun hideLoading() {
        progress_bar?.invisible()
    }

    override fun loadTeamOverviewToView(
        teamTableData: List<Table>,
        teamDetailData: List<TeamDetailItem>
    ) {
        teamDetailData[0].apply {
            val stadiumCapacity = "Capacity: $intStadiumCapacity peoples"
            txt_stadium.text = strStadium ?: "-"
            txt_capacity.text = stadiumCapacity
            Glide.with(this@TeamOverviewFragment).load(strStadiumThumb).into(img_stadium)
            Glide.with(requireContext()).load(strTeamJersey).into(img_jersey)
        }

        val pieChartData = PieChartData(tableList)
        tableList.clear()

        teamTableData[0].apply {
            tableList.add(
                SliceValue(
                    win.toFloat(),
                    ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)
                )
            )
            tableList.add(
                SliceValue(
                    draw.toFloat(),
                    ContextCompat.getColor(requireContext(), R.color.secondary_text)
                )
            )
            tableList.add(
                SliceValue(
                    loss.toFloat(),
                    ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                )
            )

            txt_win.text = win.toString()
            txt_drawn.text = draw.toString()
            txt_lose.text = loss.toString()

            pieChartData.setHasCenterCircle(true).centerText1 = "${teamTableData[0].played} Matches"
            pieChartData.centerText1FontSize = 14
            pieChartData.centerText1Typeface = Typeface.DEFAULT_BOLD
            pieChartData.centerText1Color =
                ContextCompat.getColor(requireContext(), R.color.primary_text)
        }

        chart.pieChartData = pieChartData

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

    }

    override fun onFailure(throwable: String) {
        toast(throwable)

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        overviewPresenter = null
    }
}
