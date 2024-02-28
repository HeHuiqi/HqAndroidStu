package com.example.hqandroidstu.uibase

class HqMessage(val content:String,val type:Int) {

    //注意只有在单例类、companion object或顶层方法中才可以使用const关键字
    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SEND = 1
    }
}