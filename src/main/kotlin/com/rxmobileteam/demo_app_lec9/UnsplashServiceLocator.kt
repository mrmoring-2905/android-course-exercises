package com.demo.chungha.demo.android_005.demo_app_lec9

import androidx.annotation.MainThread
import com.demo.chungha.demo.android_005.BuildConfig
import com.demo.chungha.demo.android_005.demo_app_lec9.data.remote.AuthorizationInterceptor
import com.demo.chungha.demo.android_005.demo_app_lec9.data.remote.UnsplashApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object UnsplashServiceLocator {
    const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

    @set:MainThread
    @get:MainThread
    private var _application: UnsplashApplication? = null

    @MainThread
    fun initWith(app: UnsplashApplication) {
        _application = app
    }

    @get:MainThread
    val application: UnsplashApplication
        get() = checkNotNull(_application) {
            "UnsplashServiceLocator must be initialized. " +
                    "Call UnsplashServiceLocator.initWith(this) in your Application class."
        }

    // ------------------------------------------------------------

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private val httpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }


    private val authorizationInterceptor: AuthorizationInterceptor
        get() = AuthorizationInterceptor(
            clientId = BuildConfig.UNSPLASH_CLIENT_ID
        )

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(UNSPLASH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // TODO: add AuthorizationInterceptor
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    val unsplashApiService: UnsplashApiService by lazy { UnsplashApiService(retrofit) }
}