package com.demo.chungha.demo.android_005.demo_app_lec9.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import com.demo.chungha.demo.android_005.databinding.FragmentSearchBinding
import com.demo.chungha.demo.android_005.demo_app_lec9.UnsplashServiceLocator
import com.demo.chungha.demo.android_005.demo_app_lec9.core.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by activityViewModels<SearchViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(SearchViewModel::class) {
                    SearchViewModel(
                        unsplashApiService = UnsplashServiceLocator.unsplashApiService
                    )
                }
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewPager()
        setupSearchChange()
    }

    private fun setupSearchChange() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("sangpd", "afterTextChanged: ${s.toString()}")
                viewModel.textSearchChange(s.toString())
            }

        })
    }

    private fun setupViewPager() = binding.apply {
        val searchAdapter = SearchViewPager(this@SearchFragment)
        viewPager.adapter = searchAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                SearchViewPager.SEARCH_PHOTOS_PAGE -> "Photos"
                SearchViewPager.SEARCH_USERS_PAGE -> "Users"
                else -> error("Invalid position")
            }
        }.attach()
    }

    private fun setupViews() {
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}