package com.example.hqandroidstu

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
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
    }
}