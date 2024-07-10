package com.example.hqandroidstu.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.hqandroidstu.databinding.ActivityHqServiceStuBinding
import com.example.hqandroidstu.utils.hqStartActivity

class HqServiceStuActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
//            val intent = Intent(context, HqServiceStuActivity::class.java)
//            context.startActivity(intent)

            hqStartActivity<HqServiceStuActivity>(context)
        }
    }

    private val rootBinding:ActivityHqServiceStuBinding by lazy {
        ActivityHqServiceStuBinding.inflate(layoutInflater)
    }
    private lateinit var downloadBinder: HqStuService.DownloadBinder
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder = service as HqStuService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.hqStartBtn.setOnClickListener {
            val intent = Intent(this,HqStuService::class.java)
            startService(intent)
        }
        rootBinding.hqStopBtn.setOnClickListener {
            val intent = Intent(this,HqStuService::class.java)
            stopService(intent)
        }
        rootBinding.hqBindBtn.setOnClickListener {
            val intent = Intent(this,HqStuService::class.java)
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
        }
        rootBinding.hqUnbindBtn.setOnClickListener {
            unbindService(connection)
        }
    }
}