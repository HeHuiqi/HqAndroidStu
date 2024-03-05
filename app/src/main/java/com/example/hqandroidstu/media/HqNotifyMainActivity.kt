package com.example.hqandroidstu.media

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.hqandroidstu.R
import com.example.hqandroidstu.build2
import com.example.hqandroidstu.databinding.ActivityHqNotifyMainBinding

class HqNotifyMainActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqNotifyMainActivity::class.java)
            context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqNotifyMainBinding by lazy {
        ActivityHqNotifyMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_hq_notify_main)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.hqSendBtn.setOnClickListener {
//            createNotifyChannel()
//            createNotify(this)
            requestNotifyPermission()
        }
    }
    private fun createNotifyChannel(channelId:String ="HqNotifyID",channelName:String = "发送新闻") {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
    }
    private fun createNotify(context: Context,channelId:String ="HqNotifyID") {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //设置点击通知打开的页面
        val intent = Intent(context,HqNotifyContentActivity::class.java)
        /*PendingIntent的用法同样很简单，它主要提供了几个静态方法用于获取PendingIntent的实 例，可以根据需求来选择是使用getActivity()方法、getBroadcast()方法，还是 getService()方法*/
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context,channelId)
            .setContentTitle("这是通知标题")
            //通知折叠内容
            .setContentText("这是通知内容1")
            //通知展开内容
//            .setStyle(NotificationCompat.BigTextStyle().bigText("这是通知内容这是通知内容这是通知内容这是通知内容这是通知内容这是通知内容这是通知内容这是通知内容这是通知内容这是通知内容"))
            //展开的大图
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources,R.drawable.pineapple_pic)))

            .setSmallIcon(R.drawable.apple_pic)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.banana_pic))
            .setContentIntent(pi)
            .build()
//       声明发送通知权限 <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
        manager.notify(1,notification)

    }
    private fun requestNotifyPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this,"你不允许发送App通知",Toast.LENGTH_SHORT).show()

        } else {
            createNotifyChannel()
            createNotify(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"你拒绝发送App通知",Toast.LENGTH_SHORT).show()
                } else {
                    createNotifyChannel()
                    createNotify(this)
                }
            }
        }
    }

}