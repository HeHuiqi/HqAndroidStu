package com.example.hqandroidstu

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.RequiresApi


class HqApplication:Application() {

    private var mMainHandler = android.os.Handler(Looper.getMainLooper())

    fun delayedRunInMain(delayMillis: Long, runnable: Runnable) {
        mMainHandler.postDelayed(runnable, delayMillis)
    }
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
    private fun initDebugView(c:Context) {

//        if (!Settings.canDrawOverlays(c)) {
//            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
//            intent.setData(Uri.parse("package:$packageName"))
//            startActivity(intent)
//            return
//        }
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val debugView = LayoutInflater.from(c).inflate(R.layout.hq_deubg_view,null)
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT
//        )
        val params = WindowManager.LayoutParams().apply {
            //设置大小 自适应
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        }
        windowManager.addView(debugView,params)
    }

    private fun initLifeCycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.i("HqApplication", "onActivityCreated: $activity")
                HqActivityManager.addActivity(activity)
//                delayedRunInMain(3000){
//                }
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