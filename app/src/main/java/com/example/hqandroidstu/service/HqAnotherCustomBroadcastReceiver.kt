package com.example.hqandroidstu.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class HqAnotherCustomBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context,"接收到自定义广播2",Toast.LENGTH_SHORT).show()
        //当发送有序广播时，截断广播，后面的接收器无法再接收到广播
        abortBroadcast()
    }
}