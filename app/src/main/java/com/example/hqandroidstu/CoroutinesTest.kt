package com.example.hqandroidstu

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun main(){
    println("gadgsd")
    //协程依赖线程，线程结束，协程也就结束了
    GlobalScope.launch {
        println("1232142151")
    }
    
    //这里休眠一下，等待协程任务执行完成
    Thread.sleep(1000)
}