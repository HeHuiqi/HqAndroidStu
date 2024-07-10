package com.example.hqandroidstu.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class HqAnotherCustomBroadcastReceiver : BroadcastReceiver() {
    var onReceiveBroadcast:((intent:Intent) -> Unit)? = null
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
//        val data = intent.getStringExtra("broadcast_data")
//        data?.let {
//            Toast.makeText(context,"接收到自定义广播2: $it",Toast.LENGTH_SHORT).show()
//        }
//        if (data == null) {
//            Toast.makeText(context,"接收到自定义广播2",Toast.LENGTH_SHORT).show()
//        }
        onReceiveBroadcast?.invoke(intent)

        //当发送有序广播时，截断广播，后面的接收器无法再接收到广播
        abortBroadcast()
    }
}