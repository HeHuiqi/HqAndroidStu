package com.example.hqandroidstu

//数据类
/*
* 当在一个类前 面声明了data关键字时，就表明你希望这个类是一个数据类，
* Kotlin会根据主构造函数中的参数帮你将equals()、hashCode()、toString()等固定且无实际逻辑意义的方法自动生成
* */
// brand 和 price 将作为 HqCellphone 的只读变量
data class HqCellphone(val brand:String,val price:Double) {


}