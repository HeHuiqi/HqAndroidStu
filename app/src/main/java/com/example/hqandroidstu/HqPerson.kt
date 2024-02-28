package com.example.hqandroidstu

// 在Kotlin中任何一个非抽象类默认都是不可以被继承的，相当于Java中给类声明了final 关键字
// 默认所有非抽象类都是不可以被继承的
// 在Person 类的前面加上open关键字就可以继承了
open class HqPerson {
    var name = ""
    var age = 0
    fun eat() {
        println("$name is eating. He is $age years old.")
    }
}