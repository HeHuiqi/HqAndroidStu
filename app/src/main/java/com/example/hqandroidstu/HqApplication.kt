package com.example.hqandroidstu

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log


class HqApplication:Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Log.d("HqApplication", "onCreate: ")
        //监听activity的生命周期
        initLifeCycle()
    }
    private fun initLifeCycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.i("HqApplication", "onActivityCreated: $activity")
                HqActivityManager.addActivity(activity)
                HqActivityManager.setCurrentActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
                HqActivityManager.setCurrentActivity(activity)
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                Log.i("HqApplication", "onActivityCreated: $activity")
                HqActivityManager.removeActivity(activity)
            }
        })
    }

}