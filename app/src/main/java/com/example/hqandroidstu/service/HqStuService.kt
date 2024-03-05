package com.example.hqandroidstu.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.hqandroidstu.R
import kotlin.concurrent.thread
import kotlin.math.log

class HqStuService : Service() {

    class DownloadBinder:Binder() {
        fun startDownload() {
            Log.d("HqStuService", "startDownload: ")
        }
        fun getProgress(): Int {
            Log.d("HqStuService", "getProgress: ")
            return 0
        }

    }
    private val mBinder = DownloadBinder()

    private fun createForegroundService(){
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("HqStuService","发送服务通知",NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this,HqServiceStuActivity::class.java)
        val pi = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this,"HqStuService")
            .setContentTitle("通知标题")
            .setContentText("通知内容")
            .setSmallIcon(R.drawable.pineapple_pic)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.mango_pic))
            .setContentIntent(pi)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }

    }
    override fun onCreate() {
        super.onCreate()
        //服务是在主线程运行的，处理耗时的任务，最后防止子线程
        thread {
            createForegroundService()
        }
        Log.d("HqStuService", "onCreate: ")
    }



    //通过这个方法建立和Activity之间的联系
    override fun onBind(intent: Intent): IBinder {
        Log.d("HqStuService", "onBind: ")
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("HqStuService", "onStartCommand: ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HqStuService", "onDestroy: ")
    }
}