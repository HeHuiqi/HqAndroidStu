package com.example.hqandroidstu.media

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqNotifyContentBinding

class HqNotifyContentActivity : AppCompatActivity() {

    private val rootBinding:ActivityHqNotifyContentBinding by lazy {
        ActivityHqNotifyContentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_notify_content)
        setContentView(rootBinding.root)
        cancelNotify()

    }

    private fun cancelNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //将点击的通知关闭，id为创建通知时的id保持一致
        manager.cancel(1)
        //或者取消全部
//        manager.cancelAll()
    }
}