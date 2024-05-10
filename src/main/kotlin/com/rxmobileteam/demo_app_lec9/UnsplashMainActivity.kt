package com.demo.chungha.demo.android_005.demo_app_lec9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.demo.chungha.demo.android_005.R
import com.demo.chungha.demo.android_005.databinding.ActivityUnsplashMainBinding
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.FeedsFragment
import kotlin.LazyThreadSafetyMode.NONE

class UnsplashMainActivity : AppCompatActivity() {
    private val binding by lazy(NONE) { ActivityUnsplashMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)

                add<FeedsFragment>(
                    containerViewId = R.id.container_view,
                    tag = FeedsFragment::class.java.simpleName
                )
            }
        }
    }
}