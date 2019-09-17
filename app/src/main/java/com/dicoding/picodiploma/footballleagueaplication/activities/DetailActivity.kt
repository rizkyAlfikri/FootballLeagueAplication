package com.dicoding.picodiploma.footballleagueaplication.activities

import android.graphics.Color
import android.graphics.Typeface.DEFAULT_BOLD
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.footballleagueaplication.R
import com.dicoding.picodiploma.footballleagueaplication.models.LeagueModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.nestedScrollView

class DetailActivity : AppCompatActivity() {

    // static variabel as key for save data in intent
    companion object {
        const val EXTRA_LEAGUE: String = "extra_league"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailUI().setContentView(this)

        // fetch data from intent
        val leagueExtra = intent.getParcelableExtra<LeagueModel>(EXTRA_LEAGUE)

        // initialize variabel view
        val txtName = findViewById<TextView>(R.id.txt_name)
        val txtDesc = findViewById<TextView>(R.id.txt_desc)
        val imgPhoto = findViewById<ImageView>(R.id.img_photo)

        // display data to view
        leagueExtra.apply {
            txtName.text = this.leagueName
            txtDesc.text = this.leagueDesc
            Glide.with(this@DetailActivity).load(this.leagueImage).apply(RequestOptions()).into(imgPhoto)
        }
    }

    // creating detail activity layout with anko
    class DetailUI : AnkoComponent<DetailActivity> {
        override fun createView(ui: AnkoContext<DetailActivity>) = with(ui) {
            scrollView {
                coordinatorLayout {
                    appBarLayout {
                        fitsSystemWindows = true
                        backgroundColor = Color.WHITE

                        collapsingToolbarLayout {
                            fitsSystemWindows = true
                            imageView {
                                id = R.id.img_photo
                                fitsSystemWindows = true
                                scaleType = ImageView.ScaleType.FIT_XY
                            }.lparams {
                                collapseMode = COLLAPSE_MODE_PARALLAX
                                margin = dip(16)
                                gravity = Gravity.CENTER
                            }
                            toolbar {
                                id = R.id.toolbar1
                            }.lparams(width = matchParent) {
                                collapseMode = COLLAPSE_MODE_PIN
                            }
                        }.lparams(width = matchParent, height = matchParent) {
                            scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                        }
                    }.lparams(width = matchParent, height = 600)

                    nestedScrollView {
                        verticalLayout {
                            textView {
                                id = R.id.txt_name
                                textSize = 28f
                                textColorResource =
                                    R.color.primary_text
                            }.lparams {
                                margin = dip(16)
                            }

                            textView {
                                textResource = R.string.desc
                                textSize = 20f
                                textColorResource =
                                    R.color.secondary_text
                                typeface = DEFAULT_BOLD
                            }.lparams {
                                margin = dip(16)
                            }

                            textView {
                                id = R.id.txt_desc
                                textSize = 18f
                                buildSpanned {  }
                                textColorResource =
                                    R.color.secondary_text
                            }.lparams {
                                horizontalMargin = dip(16)
                                bottomMargin = dip(16)
                            }
                        }
                    }.lparams(width = matchParent, height = matchParent){
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
                }
            }
        }
    }
}
