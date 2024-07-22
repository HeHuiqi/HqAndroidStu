package com.example.hqandroidstu.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqBroadcastStuBinding
import com.example.hqandroidstu.uibase.HqNewsMainActivity

class HqBroadcastActivityStu : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, HqBroadcastActivityStu::class.java)
            context.startActivity(intent)
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    private lateinit var timeChangeReceiver: TimeChangeReceiver
    private lateinit var customBroadcastReceiver: HqCustomBroadcastReceiver
    private lateinit var anotherCustomBroadcastReceiver: HqAnotherCustomBroadcastReceiver
     private val rootBind:ActivityHqBroadcastStuBinding by lazy {
        ActivityHqBroadcastStuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_broadcast_stu)
        setContentView(rootBind.root)
        setup()
    }
    private fun setup() {
        hideActionBar()
        initView()
        registerReceivers()

    }

    private fun initView(){
        rootBind.hqBroadcastBtn.setOnClickListener {
            //接收器手动注册或者在AndroidManifest.xml中注册
            val intent = Intent("com.example.hqandroidstu.action.HQ_BROADCAST")
            intent.setPackage(packageName)
            //设置广播数据
            intent.putExtra("closeScanPage","resp:custom_broadcast_data")
//            sendBroadcast(intent)
            //发送有序广播
            sendOrderedBroadcast(intent,null)
        }
    }
    private fun registerReceivers() {
        registerTimeChangeReceiver()
        registerCustomReceiver()
        registerCustomReceiver2()
    }
    private fun registerCustomReceiver(){
        //代码注册
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.hqandroidstu.action.HQ_BROADCAST")
        customBroadcastReceiver = HqCustomBroadcastReceiver()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(customBroadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
        }else {
            registerReceiver(customBroadcastReceiver, intentFilter)
        }
    }
    private fun registerCustomReceiver2(){
        //代码注册
        val intentFilter = IntentFilter()
        //设置优先级,优先级高的先接收到广播
        intentFilter.priority = 100
        intentFilter.addAction("com.example.hqandroidstu.action.HQ_BROADCAST")
        anotherCustomBroadcastReceiver = HqAnotherCustomBroadcastReceiver()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(anotherCustomBroadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
        }else {
            registerReceiver(anotherCustomBroadcastReceiver, intentFilter)
        }
        anotherCustomBroadcastReceiver.onReceiveBroadcast = { intent ->
            val data = intent.getStringExtra("broadcast_data")
            data?.let {
                Toast.makeText(this,"接收到自定义广播2: $it",Toast.LENGTH_SHORT).show()
            }
            if (data == null) {
                Toast.makeText(this,"接收到自定义广播2",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun registerTimeChangeReceiver(){
        //注册系统事件变化时的广播，大约1分钟1次
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver,intentFilter)
    }
    override fun onDestroy() {
        super.onDestroy()
        //注册的广播接收器，销毁时一定要移除
        unregisterReceiver(timeChangeReceiver)
        unregisterReceiver(customBroadcastReceiver)
        unregisterReceiver(anotherCustomBroadcastReceiver)
    }

    inner class TimeChangeReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context,"Time is change",Toast.LENGTH_SHORT).show()
        }
    }
}