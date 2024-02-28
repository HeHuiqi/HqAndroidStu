package com.example.hqandroidstu

//定义一个接口
interface HqStudy {
    //声明接口函数
    fun readBooks()
    // 可以提供默认实现
    fun doHomework() {
        println("这是默认实现")
    }
}