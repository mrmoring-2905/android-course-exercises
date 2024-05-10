package com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections

sealed interface CollectionsUiState {
    data object FirstPageLoading : CollectionsUiState
    data object FirstPageError : CollectionsUiState
    data class Content(
        val items: List<Item>,
        val currentPage: Int,
        val nextPageState: NextPageState
    ) : CollectionsUiState

    data class Item(
        val id: String,
        val title: String,
        val description: String,
        val coverPhotoUrl: String
    )

    sealed interface NextPageState {
        data object Loading : NextPageState
        data object Idle : NextPageState
        data object Error : NextPageState
        data object Done : NextPageState
    }
}