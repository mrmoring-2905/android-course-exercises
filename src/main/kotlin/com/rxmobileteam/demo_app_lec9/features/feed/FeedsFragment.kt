package com.demo.chungha.demo.android_005.demo_app_lec9.features.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demo.chungha.demo.android_005.R
import com.demo.chungha.demo.android_005.databinding.FragmentFeedsBinding
import com.demo.chungha.demo.android_005.demo_app_lec9.core.BaseFragment
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsFragment
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.photos.PhotosFragment
import com.demo.chungha.demo.android_005.demo_app_lec9.features.search.SearchFragment
import com.google.android.material.tabs.TabLayoutMediator

class FeedsFragment : BaseFragment<FragmentFeedsBinding>(inflate = FragmentFeedsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to SearchFragment
        binding.searchButton.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                addToBackStack(null)

                replace<SearchFragment>(
                    containerViewId = R.id.container_view,
                    tag = SearchFragment::class.java.simpleName
                )
            }
        }

        // TODO: setup ViewPager
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.run {
            adapter = FeedsViewPagerAdapter(this@FeedsFragment)

            TabLayoutMediator(
                binding.tabsLayout,
                this
            ) { tab, position ->
                tab.text = when (position) {
                    0 -> "Collections"
                    1 -> "Photos"
                    else -> error("Invalid position $position")
                }
            }.attach()
        }
    }
}

private class FeedsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CollectionsFragment.newInstance()
            1 -> PhotosFragment.newInstance()
            else -> error("Invalid position $position")
        }
    }
}