package com.example.hqandroidstu.jetpack

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class HqWorkManager(context: Context,params:WorkerParameters):Worker(context,params) {

    // doWork()方法不会运行在主线程
    override fun doWork(): Result {
        Log.d("HqWorkManager", "doWork: ")
        return Result.success()
    }
}