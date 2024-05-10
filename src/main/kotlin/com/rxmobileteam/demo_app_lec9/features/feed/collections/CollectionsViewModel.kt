package com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.chungha.demo.android_005.demo_app_lec9.data.remote.CollectionItemResponse
import com.demo.chungha.demo.android_005.demo_app_lec9.data.remote.UnsplashApiService
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsUiState.Item
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsUiState.NextPageState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class CollectionsViewModel(
    private val unsplashApiService: UnsplashApiService,
) : ViewModel() {
    private val _uiState = MutableLiveData<CollectionsUiState>(CollectionsUiState.FirstPageLoading)

    internal val uiStateLiveData: LiveData<CollectionsUiState> get() = _uiState

    init {
        loadFirstPage()
    }

    private fun loadFirstPage() {
        viewModelScope.launch {
            _uiState.value = CollectionsUiState.FirstPageLoading

            try {
                val items = unsplashApiService
                    .getCollections(
                        page = 1,
                        perPage = PER_PAGE,
                    )
                    .map { it.toItem() }
                _uiState.value = CollectionsUiState.Content(
                    items = items,
                    currentPage = 1,
                    nextPageState = NextPageState.Idle,
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                _uiState.value = CollectionsUiState.FirstPageError
            }
        }
    }

    fun loadNextPage() {
        val state = _uiState.value!!
        if (state !is CollectionsUiState.Content) {
            return
        }

        when (state.nextPageState) {
            NextPageState.Done -> return
            NextPageState.Error -> return
            NextPageState.Loading -> return
            NextPageState.Idle -> {
                _uiState.value = state.copy(nextPageState = NextPageState.Loading)

                viewModelScope.launch {
                    val nextPage = state.currentPage + 1

                    try {
                        val newItems = unsplashApiService
                            .getCollections(
                                page = nextPage,
                                perPage = PER_PAGE,
                            )
                            .map { it.toItem() }

                        _uiState.value = state.copy(
                            items = (state.items + newItems).distinctBy { it.id },
                            currentPage = nextPage,
                            nextPageState = if (newItems.size < PER_PAGE) {
                                NextPageState.Done
                            } else {
                                NextPageState.Idle
                            }
                        )
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Throwable) {
                        _uiState.value = state.copy(nextPageState = NextPageState.Error)
                    }
                }
            }
        }
    }

    private companion object {
        const val PER_PAGE = 30
    }
}

private fun CollectionItemResponse.toItem(): Item {
    return Item(
        id = id,
        title = title,
        description = "$totalPhotos photos",
        coverPhotoUrl = coverPhoto.urls.regular,
    )
}
