package com.demo.chungha.demo.android_005.demo_app_lec9.features.search.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.chungha.demo.android_005.databinding.FragmentSearchUserBinding
import com.demo.chungha.demo.android_005.demo_app_lec9.UnsplashServiceLocator
import com.demo.chungha.demo.android_005.demo_app_lec9.core.BaseFragment
import com.demo.chungha.demo.android_005.demo_app_lec9.core.debounce
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsAdapter
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsUiState
import com.demo.chungha.demo.android_005.demo_app_lec9.features.search.SearchViewModel

class SearchUserFragment :
    BaseFragment<FragmentSearchUserBinding>(inflate = FragmentSearchUserBinding::inflate) {

    private val searchViewModel by activityViewModels<SearchViewModel>(
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

    private val collectionsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CollectionsAdapter(
            requestManager = Glide.with(this@SearchUserFragment)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observerViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = collectionsAdapter
        }
    }

    private fun observerViewModel() {
        searchViewModel.queryLiveData
            .debounce(650L, searchViewModel.viewModelScope)
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) {
                searchViewModel.loadSearchResultFirstPage(1, it)
                handleLoadMore(it)
            }

        searchViewModel.uiStateLiveData.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(state: CollectionsUiState) {
        when (state) {
            is CollectionsUiState.Content -> {
                binding.progressBar.visibility =
                    if (state.nextPageState is CollectionsUiState.NextPageState.Loading) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                collectionsAdapter.submitList(state.items)
            }

            CollectionsUiState.FirstPageError -> {
                binding.progressBar.visibility = View.GONE
                collectionsAdapter.submitList(emptyList())
            }

            CollectionsUiState.FirstPageLoading -> {
                binding.progressBar.visibility = View.VISIBLE
                collectionsAdapter.submitList(emptyList())
            }
        }
    }

    private fun handleLoadMore(queryText: String) {
        val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0
                    && linearLayoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= linearLayoutManager.itemCount
                ) {
                    searchViewModel.loadNextSearchResultPage(1, queryText)
                }
            }
        })
    }

    override fun onDestroyView() {
        Log.d("sangpd", "onDestroyView: ")
        searchViewModel.textSearchChange("")
        binding.recyclerView.adapter = null
        super.onDestroyView()
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 3
        fun newInstance() = SearchUserFragment()
    }
}