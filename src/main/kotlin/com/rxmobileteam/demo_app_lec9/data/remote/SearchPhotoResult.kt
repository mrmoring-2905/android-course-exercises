package com.demo.chungha.demo.android_005.demo_app_lec9.data.remote

import com.squareup.moshi.Json

data class SearchPhotoResult(
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "results")
    val result: List<CollectionItemResponse.CoverPhoto>,
)
