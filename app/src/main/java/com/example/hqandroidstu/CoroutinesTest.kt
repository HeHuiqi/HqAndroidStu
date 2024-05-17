package com.example.hqandroidstu

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
fun base1(){
    println("gadgsd")
    //协程依赖线程，线程结束，协程也就结束了
    GlobalScope.launch {
        println("1232142151")
    }

    //这里休眠一下，等待协程任务执行完成
    Thread.sleep(1000)
}

fun simpleFlow(): Flow<Int> {
    //flow 内部会创建 suspend fun  函数，所以执行flow需要在协程域中执行
    return flow {
        delay(1000) // 假装在执行异步耗时操作
        emit(1)     // 返回值
        delay(1000)
        emit(2)
        delay(1000)
        emit(3)
        throw NullPointerException("e1")
    }
}
@OptIn(DelicateCoroutinesApi::class)
fun main(){

//    simpleFlow().onStart {
//        println("onStart1")
//    }.onCompletion { e ->
//        println("onCompletion1-e:$e")
//
//    }.catch { e->
//        println("catch-e1:$e")
//
//    }.launchIn(GlobalScope).start()

    GlobalScope.launch {
        simpleFlow().onStart {
            println("onStart")
        }.onCompletion { e ->
            println("onCompletion-e:$e")

        }.catch { e->
            println("catch-e:$e")

        }.collectLatest { v-> println("v=$v") }
    }

    GlobalScope.launch(Dispatchers.IO) {
        // 第一部分
        flow {
            emit(1)
            throw NullPointerException("e")
        }.catch {
            println("onCreate1: $it")
        }.collect {
            println("onCreate2: $it")
        }

        // 第二部分
        flow {
            emit(1)
        }.onCompletion {
            println("onCreate3: $it")
        }.collect {
            println("onCreate4: $it")
        }

        // 第三部分
        flow {
            emit(1)
            throw NullPointerException("我是异常")
        }.retryWhen { cause, attempt ->
            cause !is NullPointerException && attempt <= 2
        }.collect {
            println("onCreate5: $it")
        }
    }


    //休眠是为了让协程有时间执行完，线程结束，协程也随之消失，无法执行代码了
    Thread.sleep(50000)


}