package com.example.hqandroidstu

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(text:String,duration: Int = Toast.LENGTH_SHORT,actionText:String? =null,block:(()->Unit)? = null) {
    val snackbar =  Snackbar.make(this,text,duration)
    if (actionText != null && block != null) {
        snackbar.setAction(actionText) {
            block()
        }
    }
    snackbar.show()
}

fun View.showSnackBar(resId:Int,duration: Int = Toast.LENGTH_SHORT,actionResId:Int? =null,block:(()->Unit)? = null) {
    val snackbar =  Snackbar.make(this,resId,duration)
    if (actionResId != null && block != null) {
        snackbar.setAction(actionResId) {
            block()
        }
    }
    snackbar.show()
}
