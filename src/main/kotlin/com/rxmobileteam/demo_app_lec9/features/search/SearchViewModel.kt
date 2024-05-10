package com.demo.chungha.demo.android_005.demo_app_lec9.features.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.chungha.demo.android_005.demo_app_lec9.data.remote.CollectionItemResponse
import com.demo.chungha.demo.android_005.demo_app_lec9.data.remote.SearchUserResponseItem
import com.demo.chungha.demo.android_005.demo_app_lec9.data.remote.UnsplashApiService
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class SearchViewModel(private val unsplashApiService: UnsplashApiService) : ViewModel() {

    private val _queryLiveData = MutableLiveData<String>("")
    val queryLiveData get() = _queryLiveData
    private val _uiState = MutableLiveData<CollectionsUiState>(CollectionsUiState.FirstPageLoading)
    internal val uiStateLiveData: LiveData<CollectionsUiState>
        get() = _uiState

    /*    searchType:
     *    - 0: search photos
     *   - 1: search users
     */
    fun loadSearchResultFirstPage(searchType: Int, queryText: String) {
        Log.d("sangpd", "loadSearchResultFirstPage_searchType: $searchType - queryText: $queryText")
        viewModelScope.launch {
            _uiState.value = CollectionsUiState.FirstPageLoading
            try {
                val items = if (searchType == 0) {
                    unsplashApiService.searchPhotos(queryText, 1, 10)
                        .result
                        .map { it.toSearchPhotoItemResult() }
                } else {
                    unsplashApiService.searchUsers(queryText, 1, 10).
                    results.
                    map { it.toSearchUserItemResult() }
                }

                _uiState.value = CollectionsUiState.Content(
                    items = items,
                    currentPage = 1,
                    nextPageState = CollectionsUiState.NextPageState.Idle
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                _uiState.value = CollectionsUiState.FirstPageError
            }
        }
    }

    fun loadNextSearchResultPage(searchType: Int, queryText: String) {
        Log.d("sangpd", "loadNextSearchResultPage_searchType: $searchType - queryText: $queryText")
        val state = _uiState.value!!
        if (state !is CollectionsUiState.Content) return
        Log.d("sangpd", "loadNextPhotoPage_state.nextPageState: ${state.nextPageState}")
        when (state.nextPageState) {
            CollectionsUiState.NextPageState.Done -> return
            CollectionsUiState.NextPageState.Error -> return
            CollectionsUiState.NextPageState.Loading -> return
            CollectionsUiState.NextPageState.Idle -> {
                _uiState.value =
                    state.copy(nextPageState = CollectionsUiState.NextPageState.Loading)
                viewModelScope.launch {
                    val nextPage = state.currentPage + 1
                    try {
                        val newItems = if (searchType == 0) {
                            unsplashApiService.searchPhotos(queryText, 1, 10)
                                .result
                                .map { it.toSearchPhotoItemResult() }
                        } else {
                            unsplashApiService.searchUsers(queryText, 1, 10).
                            results.
                            map { it.toSearchUserItemResult() }
                        }

                        _uiState.value = state.copy(
                            items = (state.items + newItems).distinctBy { it.id },
                            currentPage = nextPage,
                            nextPageState = if (newItems.size < 10) {
                                CollectionsUiState.NextPageState.Done
                            } else {
                                CollectionsUiState.NextPageState.Idle
                            }
                        )
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Throwable) {
                        Log.d("sangpd", "loadNextPhotoPage_error: $e")
                        _uiState.value =
                            state.copy(nextPageState = CollectionsUiState.NextPageState.Error)
                    }
                }
            }
        }
    }

    fun textSearchChange(text: String) {
        _queryLiveData.value = text
    }
}

private fun CollectionItemResponse.CoverPhoto.toSearchPhotoItemResult(): CollectionsUiState.Item {
    return CollectionsUiState.Item(
        id = id,
        title = description ?: "",
        description = description ?: "",
        coverPhotoUrl = urls.regular
    )
}

private fun SearchUserResponseItem.Result.toSearchUserItemResult(): CollectionsUiState.Item {
    return CollectionsUiState.Item(
        id = id,
        title = username,
        description = "Location: $location (total photos: $totalPhotos)",
        coverPhotoUrl = profileImage.large
    )
}