package com.example.hqandroidstu

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


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
//        throw NullPointerException("e1")
    }
}
fun simpleFlowTest() {
    simpleFlow().onStart {
        println("onStart1")
    }.onEach { v ->
        println("flow value:$v")
    }.onCompletion { e ->
        println("onCompletion1-e:$e")

    }.catch { e->
        println("catch-e1:$e")

    }.launchIn(MainScope()).start()

}
fun simpleFlowTest2() {
    CoroutineScope(Job()).launch {
        simpleFlow().onStart {
            println("onStart")
        }.onEach { v ->
            //接收每次emit的数据
            println("flow value:$v")

        }
            .onCompletion { e ->
                println("onCompletion-e:$e")

            }.catch { e->
                println("catch-e:$e")

            }.collectLatest { v-> println("v=$v") }
    }
}
fun  flowTest() {
    // 会阻塞当前线程直到其内部所有协程执行完毕，内部的协程彼此之间依旧是非阻式。用于把阻塞式的普通函数内部改为协程式编写，由于会阻塞线程在开发中不会使用，一般用于main函数作测试，单元测试一般使用runTest
    runBlocking {
        val simple = flow {
            listOf("Hello", "world", "of", "flows!")
                .forEach {
                    delay(100)
                    //发送数据
                    emit(it)
                }
        }
        simple.collect {
            //接收数据
            println(it)
        }
    }
}
fun  flowTest2() {
    CoroutineScope(Job()).launch(Dispatchers.IO) {
        // 第一部分
        flow {
            //发送数据
            emit(1)
            throw NullPointerException("e")
        }.catch {
            println("onCreate1: $it")
        }.collect {
            println("onCreate2: $it")
        }

        // 第二部分
        flow {
            //发送数据
            emit(1)
        }.onCompletion {
            println("onCreate3: $it")
        }.collect {
            //接收数据
            println("onCreate4: $it")
        }

        // 第三部分
        flow {
            //发送数据
            emit(1)
            throw NullPointerException("我是异常")
        }.retryWhen { cause, attempt ->
            cause !is NullPointerException && attempt <= 2
        }.collect {
            //接收数据
            println("onCreate5: $it")
        }
    }

}
fun  flowTest3() {
    val scope = CoroutineScope(Job()+Dispatchers.IO)

    scope.launch {
        val simple = flow {
            listOf("Hello", "world", "of", "flows!")
                .forEach {
                    delay(100)
                    //发送数据
                    emit(it)
                }
        }
        simple.collect {
            //打印数据
            println(it)
        }
    }
    scope.cancel()    //手动在生命周期里释放资源

}
fun  flowTest4() {
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    scope.launch {
        val fw =  flow {
            for (i in 1..3) {
                delay(100)
                emit(i)
            }
        }
        //在这里可以做数据的转换，重新发射
        fw.transform {
            emit("new v ${it * it}")
        }.catch {
            println("fw catch e:$it")
        }.collect{
            println("fw v:$it")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun main(){

//    simpleFlowTest()
//    simpleFlowTest2()
//    flowTest()
//    flowTest2()
//    flowTest3()
    flowTest4()




    //休眠是为了让协程有时间执行完，线程结束，协程也随之消失，无法执行代码了
    Thread.sleep(2000)


}