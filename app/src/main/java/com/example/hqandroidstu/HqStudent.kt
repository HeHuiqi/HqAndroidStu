package com.example.hqandroidstu

// 继承使用 :
// 实现接口 HqStudy
class HqStudent : HqPerson(),HqStudy{
    var sno = ""
    var grade = 0


    // 实现接口函数
    override fun readBooks() {
        println("$name is reading")
    }

    override fun doHomework() {
        println("$name is doing homework")
    }
}

