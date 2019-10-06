package com.dicoding.picodiploma.footballleagueaplication.features.favorites


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.db.FavoritesModel
import com.dicoding.picodiploma.footballleagueaplication.db.database
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity
import com.dicoding.picodiploma.footballleagueaplication.features.detailMatch.DetailMatchActivity.Companion.EXTRA_EVENT
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FavoriteFragment : Fragment() {
    private val favoriteAdapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // initialize recycler view
        rv_favorite.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        // show favorite data from database
        showFavorite()

    }

    // show favorite data from database
    // this override method will make our favorite list can automatically update
    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        // fetch data from database
        favoriteAdapter.clear()
        context?.database?.use {
            val result = select(FavoritesModel.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoritesModel>())

            // load favorite data to recycler view
            favorite.map { it ->
                favoriteAdapter.add(
                    FavoriteHolder(
                        it
                    ) {
                        startActivity<DetailMatchActivity>(EXTRA_EVENT to it.idEvent)
                    })
            }

        }
    }
}
