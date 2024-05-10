package com.demo.chungha.demo.android_005.demo_app_lec9.core

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

fun <T> LiveData<T>.debounce(duration: Long, coroutineScope: CoroutineScope): LiveData<T> {
    val mdl = MediatorLiveData<T>()
    var job: Job? = null

    mdl.addSource(this) { value ->
        job?.cancel()
        job = coroutineScope.launch {
            delay(duration)
            mdl.value = value
        }
    }
    return mdl
}