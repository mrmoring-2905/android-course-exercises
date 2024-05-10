package com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.chungha.demo.android_005.databinding.FragmentPhotosBinding
import com.demo.chungha.demo.android_005.demo_app_lec9.UnsplashServiceLocator
import com.demo.chungha.demo.android_005.demo_app_lec9.core.BaseFragment
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsAdapter
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsUiState

class PhotosFragment : BaseFragment<FragmentPhotosBinding>(inflate = FragmentPhotosBinding::inflate) {

    private val photoListViewModel by viewModels<PhotoViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(PhotoViewModel::class) {
                    PhotoViewModel(unsplashApiService =  UnsplashServiceLocator.unsplashApiService)
                }
            }
        }
    )

    private val feedPhotoAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CollectionsAdapter(
            requestManager = Glide.with(this@PhotosFragment)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setUpViewModel()
    }

    private fun setupViews() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = feedPhotoAdapter
        }
    }

    private fun setUpViewModel() {
        photoListViewModel.uiStateLiveData.observe(viewLifecycleOwner, ::renderState)
        handleLoadMore()
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        super.onDestroyView()
    }

    private fun handleLoadMore() {
        val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0
                    && linearLayoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= linearLayoutManager.itemCount
                ) {
                    photoListViewModel.loadNextPhotoPage()
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

                feedPhotoAdapter.submitList(state.items)
            }

            CollectionsUiState.FirstPageError -> {
                binding.progressBar.visibility = View.GONE

                feedPhotoAdapter.submitList(emptyList())
            }

            CollectionsUiState.FirstPageLoading -> {
                binding.progressBar.visibility = View.VISIBLE

                feedPhotoAdapter.submitList(emptyList())
            }
        }
    }

    companion object {
        const val VISIBLE_THRESHOLD = 3
        fun newInstance() = PhotosFragment()
    }
}