package com.dicoding.picodiploma.footballleagueaplication.features.playerdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.playerdetail.PlayerDetailItem
import com.dicoding.picodiploma.footballleagueaplication.models.playerhonors.PlayerHonorsItem
import com.dicoding.picodiploma.footballleagueaplication.repository.PlayerDetailRepository
import com.dicoding.picodiploma.footballleagueaplication.utils.EspressoIdlingResource
import com.dicoding.picodiploma.footballleagueaplication.utils.ageConversion
import com.dicoding.picodiploma.footballleagueaplication.utils.invisible
import com.dicoding.picodiploma.footballleagueaplication.utils.visible
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.PlayerDetailInfoHolder
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.PlayerHeaderHonorHolder
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.PlayerHonorHolder
import com.dicoding.picodiploma.footballleagueaplication.viewHolder.PlayerMediaHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.activity_player_detail.img_player
import org.jetbrains.anko.toast

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    private val playerDetailAdapter: GroupAdapter<ViewHolder> = GroupAdapter()
    private var playerDetailPresenter: PlayerDetailPresenter? = null
    private val listPlayerDetailInfo = mutableListOf<PlayerDetailItem>()
    private val listPlayerHonor = mutableListOf<PlayerHonorsItem>()

    companion object {
        const val EXTRA_PLAYER: String = "extraPlayer"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        val idPlayer = intent.getStringExtra(EXTRA_PLAYER)

        playerDetailAdapter.clear()

        rv_player.apply {
            layoutManager = LinearLayoutManager(this@PlayerDetailActivity)
            setHasFixedSize(true)
            adapter = playerDetailAdapter
        }

        listPlayerDetailInfo.clear()
        listPlayerHonor.clear()

        EspressoIdlingResource.incrementIdle()
        playerDetailPresenter = PlayerDetailPresenter(this, PlayerDetailRepository())
        playerDetailPresenter?.getPlayerDetail(idPlayer)

        collapsing_toolbar.apply {
            setCollapsedTitleTextColor(
                ContextCompat.getColor(
                    this@PlayerDetailActivity,
                    android.R.color.white
                )
            )
            setExpandedTitleColor(
                ContextCompat.getColor(
                    this@PlayerDetailActivity,
                    android.R.color.transparent
                )
            )
        }
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun loadPlayerToView(listPlayerDetail: List<PlayerDetailItem>) {

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }

        listPlayerDetailInfo.addAll(listPlayerDetail)
        listPlayerDetail[0].apply {
            txt_player.text = strPlayer
            txt_position.text = strPosition
            txt_age.text = ageConversion(dateBorn)
            txt_weight.text = if (!strWeight.isNullOrBlank()) strWeight ?: " - " else " - "
            txt_height.text = if (!strHeight.isNullOrBlank()) strHeight ?: " - " else " - "

            Glide.with(this@PlayerDetailActivity).load(strCutout)
                .apply(RequestOptions().override(100, 100)).into(img_player)

            Glide.with(this@PlayerDetailActivity).load(strFanart1).into(img_banner)

            collapsing_toolbar.title = strPlayer
        }

        // load sosial media player
        val followPlayer = getString(R.string.follow)
        listPlayerDetail.map {
            playerDetailAdapter.add(
                PlayerMediaHolder(
                    this,
                    "$followPlayer ${it.strPlayer}",
                    it.strFacebook,
                    it.strTwitter,
                    it.strInstagram
                )
            )


            Section().apply {
                add(PlayerDetailInfoHolder(it))
                playerDetailAdapter.add(this)
            }
        }

        Section().apply {
            add(PlayerHeaderHonorHolder())
            playerDetailAdapter.add(this)
        }

        listPlayerHonor.map {
            Section().apply {
                add(PlayerHonorHolder(it))
                playerDetailAdapter.add(this)
            }
        }

    }

    override fun loadPlayerHonorToView(listPlayerHonorsItem: List<PlayerHonorsItem>) {
        listPlayerHonor.addAll(listPlayerHonorsItem)
    }

    override fun onFailure(throwable: String) {
        toast(throwable)
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrementIdle()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playerDetailPresenter = null
    }

}
