package com.example.hqandroidstu

import android.content.Context
import android.widget.Toast

// "hello".showToast(context)
fun String.showToast(context: Context? = null, duration: Int = Toast.LENGTH_SHORT) {
    var ct = HqApplication.context
    if (context != null) {
        ct = context
    }
    Toast.makeText(ct,this,duration).show()
}

fun Int.showToast(context: Context? = null,duration: Int = Toast.LENGTH_SHORT) {
    var ct = HqApplication.context
    if (context != null) {
        ct = context
    }
    Toast.makeText(ct,this,duration).show()
}
