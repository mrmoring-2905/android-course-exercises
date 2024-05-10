package com.demo.chungha.demo.android_005.demo_app_lec9.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CollectionItemResponse(
    @Json(name = "id")
    val id: String, // UkoUlL6m1zY
    @Json(name = "title")
    val title: String, // 9-5
    @Json(name = "description")
    val description: String?, // Explore the vast beauty of the planet we call home.
    @Json(name = "published_at")
    val publishedAt: String, // 2024-04-29T15:06:43Z
    @Json(name = "last_collected_at")
    val lastCollectedAt: String, // 2024-05-06T18:07:39Z
    @Json(name = "updated_at")
    val updatedAt: String, // 2024-05-06T18:07:39Z
    @Json(name = "featured")
    val featured: Boolean, // true
    @Json(name = "total_photos")
    val totalPhotos: Int, // 65
    @Json(name = "private")
    val `private`: Boolean, // false
    @Json(name = "share_key")
    val shareKey: String, // d44beb13b08e97473f772841e80fd86c
    @Json(name = "links")
    val links: Links,
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto,
    @Json(name = "preview_photos")
    val previewPhotos: List<PreviewPhoto>
) {

    @Keep
    data class Links(
        @Json(name = "self")
        val self: String, // https://api.unsplash.com/collections/UkoUlL6m1zY
        @Json(name = "html")
        val html: String, // https://unsplash.com/collections/UkoUlL6m1zY/9-5
        @Json(name = "photos")
        val photos: String, // https://api.unsplash.com/collections/UkoUlL6m1zY/photos
        @Json(name = "related")
        val related: String // https://api.unsplash.com/collections/UkoUlL6m1zY/related
    )

    @Keep
    data class CoverPhoto(
        @Json(name = "id")
        val id: String, // YB9FpU2a9go
        @Json(name = "slug")
        val slug: String, // a-woman-sitting-in-a-chair-with-a-piece-of-paper-on-her-face-YB9FpU2a9go
        @Json(name = "created_at")
        val createdAt: String, // 2024-01-13T17:16:04Z
        @Json(name = "updated_at")
        val updatedAt: String, // 2024-05-06T20:46:17Z
        @Json(name = "promoted_at")
        val promotedAt: String?, // 2024-04-11T08:45:06Z
        @Json(name = "width")
        val width: Int, // 4480
        @Json(name = "height")
        val height: Int, // 6720
        @Json(name = "color")
        val color: String, // #d9d9d9
        @Json(name = "blur_hash")
        val blurHash: String?, // LPO{,YR*lUayy?t8rWj?m-V@SvfP
        @Json(name = "description")
        val description: String?, // Teardrops
        @Json(name = "alt_description")
        val altDescription: String? = null, // a woman sitting in a chair with a piece of paper on her face
        @Json(name = "urls")
        val urls: Urls,
        @Json(name = "links")
        val links: Links,
        @Json(name = "likes")
        val likes: Int, // 101
        @Json(name = "liked_by_user")
        val likedByUser: Boolean, // false
        @Json(name = "current_user_collections")
        val currentUserCollections: List<Any>,
        @Json(name = "sponsorship")
        val sponsorship: Any?, // null
        @Json(name = "asset_type")
        val assetType: String, // photo
    ) {

        @Keep
        data class Urls(
            @Json(name = "raw")
            val raw: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3
            @Json(name = "full")
            val full: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb
            @Json(name = "regular")
            val regular: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max
            @Json(name = "small")
            val small: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max
            @Json(name = "thumb")
            val thumb: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max
            @Json(name = "small_s3")
            val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/unsplash-premium-photos-production/premium_photo-1705091981580-61fde359c314
        )

        @Keep
        data class Links(
            @Json(name = "self")
            val self: String, // https://api.unsplash.com/photos/a-woman-sitting-in-a-chair-with-a-piece-of-paper-on-her-face-YB9FpU2a9go
            @Json(name = "html")
            val html: String, // https://unsplash.com/photos/a-woman-sitting-in-a-chair-with-a-piece-of-paper-on-her-face-YB9FpU2a9go
            @Json(name = "download")
            val download: String, // https://unsplash.com/photos/YB9FpU2a9go/download
            @Json(name = "download_location")
            val downloadLocation: String // https://api.unsplash.com/photos/YB9FpU2a9go/download
        )
    }

    @Keep
    data class PreviewPhoto(
        @Json(name = "id")
        val id: String, // YB9FpU2a9go
        @Json(name = "slug")
        val slug: String, // a-woman-sitting-in-a-chair-with-a-piece-of-paper-on-her-face-YB9FpU2a9go
        @Json(name = "created_at")
        val createdAt: String, // 2024-01-13T17:16:04Z
        @Json(name = "updated_at")
        val updatedAt: String, // 2024-05-06T20:46:17Z
        @Json(name = "blur_hash")
        val blurHash: String?, // LPO{,YR*lUayy?t8rWj?m-V@SvfP
        @Json(name = "asset_type")
        val assetType: String, // photo
        @Json(name = "urls")
        val urls: Urls
    ) {
        @Keep
        data class Urls(
            @Json(name = "raw")
            val raw: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3
            @Json(name = "full")
            val full: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb
            @Json(name = "regular")
            val regular: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max
            @Json(name = "small")
            val small: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max
            @Json(name = "thumb")
            val thumb: String, // https://plus.unsplash.com/premium_photo-1705091981580-61fde359c314?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max
            @Json(name = "small_s3")
            val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/unsplash-premium-photos-production/premium_photo-1705091981580-61fde359c314
        )
    }
}