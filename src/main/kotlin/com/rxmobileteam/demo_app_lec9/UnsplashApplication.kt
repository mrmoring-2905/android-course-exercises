package com.demo.chungha.demo.android_005.demo_app_lec9

import android.app.Application

class UnsplashApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO: init service locator
        UnsplashServiceLocator.initWith(this)
    }
}