package com.example.hqandroidstu.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class HqBootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            Toast.makeText(context,"Boot Completed",Toast.LENGTH_SHORT).show()
        }
    }
}