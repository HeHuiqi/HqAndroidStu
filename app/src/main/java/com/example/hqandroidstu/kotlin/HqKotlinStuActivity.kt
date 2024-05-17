package com.example.hqandroidstu.kotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqKotlinStuBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HqKotlinStuActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqKotlinStuActivity::class.java)
               context.startActivity(intent)
        }
    }

    private val mJob4Looper by lazy { Job() }
    private val mCoroutineScope4Looper by lazy { CoroutineScope(mJob4Looper) }
    private var mShouldPauseLooper4MsgInfo = false
    private var count = 1

    private val rootBinding:ActivityHqKotlinStuBinding by lazy {
        ActivityHqKotlinStuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.hqCodeBtn.setOnClickListener {
            startLooper4MsgInfo()
            val product =HqProduct("冰红茶",3.5,"上海")
            HqDataPassShowActivity.actionStart(this,product)
        }
    }

    private fun startLooper4MsgInfo() {
        val  periodTimeInMs = 1000L
        mCoroutineScope4Looper.launch(Dispatchers.IO) {
            while (isActive) {
                if (!mShouldPauseLooper4MsgInfo) {
                    //延迟一秒执行
                    delay(periodTimeInMs)
                    withContext(Dispatchers.Main) {
                        Log.i("startLooper4MsgInfo", "looperTest: $count")
                    }
                }
                if (count >= 10) {
                    Log.i("startLooper4MsgInfo", "Scope4Looper.cancel:$count ")
                    mCoroutineScope4Looper.cancel()
                }
                count++

            }
        }
    }
}