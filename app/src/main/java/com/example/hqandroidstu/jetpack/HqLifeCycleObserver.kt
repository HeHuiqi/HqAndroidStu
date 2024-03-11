package com.example.hqandroidstu.jetpack

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.math.log

class HqLifeCycleObserver(val lifecycle: Lifecycle):LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart() {
        Log.d("HqLifeCycleObserver", "activityStart: ")
        //通过传入的lifecycle来主动获取当前状态
        // 共有INITIALIZED、DESTROYED、CREATED、STARTED、RESUMED这5种状态类型
        Log.d("HqLifeCycleObserver", "activityStart: ${lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop() {
        Log.d("HqLifeCycleObserver", "activityStop: ")
    }
}