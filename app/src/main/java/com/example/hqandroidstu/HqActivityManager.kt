package com.example.hqandroidstu

import android.annotation.SuppressLint
import android.app.Activity
import java.lang.ref.WeakReference




@SuppressLint("StaticFieldLeak")
object HqActivityManager {
    private val activitys = ArrayList<Activity>()
    private var currentActivityWeakRef: WeakReference<Activity>? = null
    fun addActivity(activity: Activity) {
        activitys.add(activity)
    }
    fun removeActivity(activity: Activity) {
        activitys.remove(activity)
        currentActivityWeakRef = null
    }

    fun getCurrentActivity():Activity?{
        return currentActivityWeakRef?.get()
    }
    fun setCurrentActivity(activity: Activity) {
        currentActivityWeakRef = WeakReference<Activity>(activity)
    }

    fun finishAll() {
        for (activity in activitys) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activitys.clear()
        //杀掉当前进程
//        android.os.Process.killProcess(android.os.Process.myPid())
    }
}