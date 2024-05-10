package com.demo.chungha.demo.android_005.demo_app_lec9.data.remote


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class SearchUserResponseItem(
    @Json(name = "total")
    val total: Int, // 281
    @Json(name = "total_pages")
    val totalPages: Int, // 29
    @Json(name = "results")
    val results: List<Result>
) {
    @Keep
    data class Result(
        @Json(name = "id")
        val id: String, // PI1IJ-z5hMo
        @Json(name = "updated_at")
        val updatedAt: String, // 2024-02-28T01:25:13Z
        @Json(name = "username")
        val username: String, // nabil_
        @Json(name = "name")
        val name: String, // Nab Nas
        @Json(name = "first_name")
        val firstName: String, // Nab
        @Json(name = "last_name")
        val lastName: String?, // Nas
        @Json(name = "twitter_username")
        val twitterUsername: String?, // Dino wbot
        @Json(name = "portfolio_url")
        val portfolioUrl: Any?, // null
        @Json(name = "bio")
        val bio: String?, // Take By One Plus 8 Pro 
        @Json(name = "location")
        val location: String?, // Indonesia
        @Json(name = "links")
        val links: Links,
        @Json(name = "profile_image")
        val profileImage: ProfileImage,
        @Json(name = "instagram_username")
        val instagramUsername: String?, // nabil._._
        @Json(name = "total_collections")
        val totalCollections: Int, // 0
        @Json(name = "total_likes")
        val totalLikes: Int, // 11
        @Json(name = "total_photos")
        val totalPhotos: Int, // 40
        @Json(name = "total_promoted_photos")
        val totalPromotedPhotos: Int, // 0
        @Json(name = "total_illustrations")
        val totalIllustrations: Int, // 0
        @Json(name = "total_promoted_illustrations")
        val totalPromotedIllustrations: Int, // 0
        @Json(name = "accepted_tos")
        val acceptedTos: Boolean, // true
        @Json(name = "for_hire")
        val forHire: Boolean, // false
        @Json(name = "social")
        val social: Social,
        @Json(name = "followed_by_user")
        val followedByUser: Boolean, // false
        @Json(name = "photos")
        val photos: List<Photo>
    ) {
        @Keep
        data class Links(
            @Json(name = "self")
            val self: String, // https://api.unsplash.com/users/nabil_
            @Json(name = "html")
            val html: String, // https://unsplash.com/@nabil_
            @Json(name = "photos")
            val photos: String, // https://api.unsplash.com/users/nabil_/photos
            @Json(name = "likes")
            val likes: String, // https://api.unsplash.com/users/nabil_/likes
            @Json(name = "portfolio")
            val portfolio: String, // https://api.unsplash.com/users/nabil_/portfolio
            @Json(name = "following")
            val following: String, // https://api.unsplash.com/users/nabil_/following
            @Json(name = "followers")
            val followers: String // https://api.unsplash.com/users/nabil_/followers
        )

        @Keep
        data class ProfileImage(
            @Json(name = "small")
            val small: String, // https://images.unsplash.com/profile-1620326900500-43f92c1387dbimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
            @Json(name = "medium")
            val medium: String, // https://images.unsplash.com/profile-1620326900500-43f92c1387dbimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
            @Json(name = "large")
            val large: String // https://images.unsplash.com/profile-1620326900500-43f92c1387dbimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
        )

        @Keep
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String?, // nabil._._
            @Json(name = "portfolio_url")
            val portfolioUrl: Any?, // null
            @Json(name = "twitter_username")
            val twitterUsername: String?, // Dino wbot
            @Json(name = "paypal_email")
            val paypalEmail: Any? // null
        )

        @Keep
        data class Photo(
            @Json(name = "id")
            val id: String, // Q4BUDjTdYIc
            @Json(name = "slug")
            val slug: String, // Q4BUDjTdYIc
            @Json(name = "created_at")
            val createdAt: String, // 2021-05-06T19:10:13Z
            @Json(name = "updated_at")
            val updatedAt: String, // 2024-05-09T12:53:17Z
            @Json(name = "blur_hash")
            val blurHash: String, // L98;6_oyR*S20Mn%xts:XRWBNGoL
            @Json(name = "asset_type")
            val assetType: String, // photo
            @Json(name = "urls")
            val urls: Urls
        ) {
            @Keep
            data class Urls(
                @Json(name = "raw")
                val raw: String, // https://images.unsplash.com/photo-1620328099809-152cf886f552?ixlib=rb-4.0.3
                @Json(name = "full")
                val full: String, // https://images.unsplash.com/photo-1620328099809-152cf886f552?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb
                @Json(name = "regular")
                val regular: String, // https://images.unsplash.com/photo-1620328099809-152cf886f552?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max
                @Json(name = "small")
                val small: String, // https://images.unsplash.com/photo-1620328099809-152cf886f552?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max
                @Json(name = "thumb")
                val thumb: String, // https://images.unsplash.com/photo-1620328099809-152cf886f552?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max
                @Json(name = "small_s3")
                val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1620328099809-152cf886f552
            )
        }
    }
}