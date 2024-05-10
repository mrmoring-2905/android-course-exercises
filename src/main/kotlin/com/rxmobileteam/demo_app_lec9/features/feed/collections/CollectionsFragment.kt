package com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.chungha.demo.android_005.databinding.FragmentCollectionsBinding
import com.demo.chungha.demo.android_005.demo_app_lec9.UnsplashServiceLocator
import com.demo.chungha.demo.android_005.demo_app_lec9.core.BaseFragment

class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>(inflate = FragmentCollectionsBinding::inflate) {

    private val viewModel by viewModels<CollectionsViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(CollectionsViewModel::class) {
                    CollectionsViewModel(
                        unsplashApiService = UnsplashServiceLocator.unsplashApiService
                    )
                }
            }
        }
    )

    private val collectionsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CollectionsAdapter(
            requestManager = Glide.with(this@CollectionsFragment)
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        bindViewModel()
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        super.onDestroyView()
    }

    private fun setupViews() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = collectionsAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner, ::renderState)
        handleLoadMore()
    }

    private fun handleLoadMore() {
        val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0
                    && linearLayoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= linearLayoutManager.itemCount
                ) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun renderState(state: CollectionsUiState) {
        when (state) {
            is CollectionsUiState.Content -> {
                binding.progressBar.visibility = if (state.nextPageState is CollectionsUiState.NextPageState.Loading) {
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


    companion object {
        fun newInstance() = CollectionsFragment()

        private const val VISIBLE_THRESHOLD = 3
    }
}
