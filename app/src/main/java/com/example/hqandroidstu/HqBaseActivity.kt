package com.example.hqandroidstu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

open class HqBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_base)
        Log.d("HqBaseActivity", javaClass.simpleName)
        //初始化时添加
        HqActivityManager.addActivity(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        //销毁时移除
        HqActivityManager.removeActivity(this)
    }
}