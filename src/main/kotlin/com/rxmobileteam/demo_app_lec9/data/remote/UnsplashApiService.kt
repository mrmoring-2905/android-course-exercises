package com.demo.chungha.demo.android_005.demo_app_lec9.data.remote

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {
    // TODO: Add API endpoints here

    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<CollectionItemResponse>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchPhotoResult

    @GET("photos")
    suspend fun getPhotoList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<PhotoResponseItem>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchUserResponseItem

    companion object {
        operator fun invoke(retrofit: Retrofit): UnsplashApiService = retrofit.create()
    }
}