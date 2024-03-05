package com.example.hqandroidstu.data

import android.content.ContentValues

//cvOf()方 法接收了一个Pair参数，也就是使用A to B语法结构创建出来的参数类型
// vararg对应的就是Java中的可变参数 列表，我们允许向这个方法传入0个、1个、2个甚至任意多个Pair类型的参数
fun cvOf(vararg pairs: Pair<String,Any?>):ContentValues {
    val cv = ContentValues()
    for (pair in pairs) {
        val key = pair.first
        when (val value = pair.second) {
            is Int -> cv.put(key,value)
            is Long -> cv.put(key,value)
            is Float -> cv.put(key,value)
            is Double -> cv.put(key,value)
            is Boolean -> cv.put(key,value)
            is String -> cv.put(key,value)
            is Byte -> cv.put(key,value)
            is ByteArray -> cv.put(key,value)
            null -> cv.putNull(key)
        }
    }
    return cv

}