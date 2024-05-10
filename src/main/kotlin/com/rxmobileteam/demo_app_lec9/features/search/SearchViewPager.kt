package com.demo.chungha.demo.android_005.demo_app_lec9.features.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demo.chungha.demo.android_005.demo_app_lec9.features.search.photos.SearchPhotosFragment
import com.demo.chungha.demo.android_005.demo_app_lec9.features.search.user.SearchUserFragment

class SearchViewPager(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            SEARCH_PHOTOS_PAGE -> SearchPhotosFragment.newInstance()
            SEARCH_USERS_PAGE -> SearchUserFragment.newInstance()
            else -> error("Invalid position")
        }
    }

    companion object {
        const val SEARCH_PHOTOS_PAGE = 0
        const val SEARCH_USERS_PAGE = 1
    }
}
