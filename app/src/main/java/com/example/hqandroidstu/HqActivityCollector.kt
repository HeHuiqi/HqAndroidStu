package com.example.hqandroidstu

import android.app.Activity

object HqActivityCollector {
    private val activitys = ArrayList<Activity>()
    fun addActivity(activity: Activity) {
        activitys.add(activity)
    }
    fun removeActivity(activity: Activity) {
        activitys.remove(activity)
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