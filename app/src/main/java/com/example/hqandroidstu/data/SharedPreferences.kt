package com.example.hqandroidstu.data

import android.content.SharedPreferences

//向SharedPreferences类中添加了一个open函数
fun SharedPreferences.open(block:SharedPreferences.Editor.()->Unit) {
    val  editor = edit()
    editor.block()
    editor.apply()
}