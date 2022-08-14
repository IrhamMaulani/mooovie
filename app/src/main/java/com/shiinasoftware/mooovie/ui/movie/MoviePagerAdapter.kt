package com.shiinasoftware.mooovie.ui.movie

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shiinasoftware.mooovie.R

class MoviePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    val TAB_TITLES: IntArray = intArrayOf(
        R.string.detail,
        R.string.reviews,
        R.string.trailer
    )

    override fun createFragment(position: Int) = when (position) {
        0 -> MovieDetailFragment()
        1 -> MovieReviewFragment()
        2 -> MovieTrailerFragment()
        else -> MovieDetailFragment()
    }


}