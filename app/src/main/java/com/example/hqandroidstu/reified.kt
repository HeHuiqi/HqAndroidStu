package com.example.hqandroidstu

import android.content.Context
import android.content.Intent


//范型实化
// 该函数必须是内联函数才行，也就是要用inline 关键字来修饰该函数。其次，在声明泛型的地方必须加上reified关键字来表示该泛型要进行 实化

// 获取一个类的示例
// 使用 getGenericType<String>()
inline fun <reified T> getGenericType() = T::class.java

// 该函数接收一个Context参数，并同时使用 inline和reified关键字让泛型T成为了一个被实化的泛型
// 使用 startActivity<MainActivity>(context)
inline fun <reified T> hqStartActivity(context: Context) {
    val intent = Intent(context,T::class.java)
    context.startActivity(intent)
}