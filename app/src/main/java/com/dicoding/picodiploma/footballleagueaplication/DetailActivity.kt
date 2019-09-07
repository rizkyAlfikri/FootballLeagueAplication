package com.dicoding.picodiploma.footballleagueaplication

import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout

class DetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_LEAGUE: String = "extra_league"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailUI().setContentView(this)


    }

    class DetailUI : AnkoComponent<DetailActivity> {
        override fun createView(ui: AnkoContext<DetailActivity>) = with(ui) {
            scrollView {
                coordinatorLayout {
                    appBarLayout {
                        fitsSystemWindows = true
                        collapsingToolbarLayout {
                            fitsSystemWindows = true

                            imageView {
                                id = R.id.img_photo
                                fitsSystemWindows = true
                                scaleType = ImageView.ScaleType.FIT_XY
                            }.lparams(width = matchParent, height = matchParent){
                                collapseMode = COLLAPSE_MODE_PARALLAX
                            }

                            toolbar {
                                id = R.id.toolbar
                            }.lparams(width = matchParent){
                                collapseMode = COLLAPSE_MODE_PIN

                            }
                        }.lparams(width = matchParent, height = matchParent){
                            scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                        }
                    }.lparams(width = matchParent, height = 300)

                    verticalLayout {
                        textView{
                            id = R.id.txt_name
                            textSize = sp(18).toFloat()
                            textColor = R.color.primary_text
                        }.lparams{
                            margin = dip(16)
                        }

                        textView{
                            text = R.string.desc.toString()
                            textSize = sp(16).toFloat()
                            textColor = R.color.secondary_text
                            BOLD
                        }.lparams {
                            margin = dip(16)
                        }

                        textView {
                            id = R.id.txt_desc
                            textSize = sp(14).toFloat()
                            textColor = R.color.secondary_text
                        }.lparams{
                            horizontalMargin = dip(16)
                            bottomMargin = dip(16)
                        }
                    }
                }
            }
        }

    }
}
