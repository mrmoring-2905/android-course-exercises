package com.demo.chungha.demo.android_005.demo_app_lec9.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.demo.chungha.demo.android_005.demo_app_lec9.UnsplashServiceLocator
import java.io.InputStream

@GlideModule
class UnsplashGlideModule: AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(
                UnsplashServiceLocator.okHttpClient
            )
        )
    }
}