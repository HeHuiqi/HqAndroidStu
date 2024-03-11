package com.example.hqandroidstu.jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HqJetpackStuViewModel2(private val initCount:Int) : ViewModel() {
    //通过LiveData 我们就可以通过添加观察者，实时监听其变化
    //任何LiveData对象都可以调用它的observe()方法来观察数据的变化
    /*
    MutableLiveData是一种可变的LiveData，它的用法很简单，主要 有3种读写数据的方法，分别是getValue()、setValue()和postValue()方法。 getValue()方法用于获取LiveData中包含的数据;setValue()方法用于给LiveData设置数 据，但是只能在主线程中调用;
    postValue()方法用于在非主线程中给LiveData设置数据
    子线程中给LiveData设置数据，一定要调用postValue()方法， 而不能再使用setValue()方法，否则会发生崩溃
    * */

    //对外暴露只读数据
    val counter:LiveData<Int>
        get() = _counter

    //对内操作数据
    private var _counter = MutableLiveData<Int>()

    init {
        _counter.value = initCount
    }
    fun plusOne() {
        val count = _counter.value ?:0
        _counter.value = count + 1
    }
    fun clear() {
        _counter.value = 0
    }
    fun currentCount() : Int  = _counter.value ?: 0
}