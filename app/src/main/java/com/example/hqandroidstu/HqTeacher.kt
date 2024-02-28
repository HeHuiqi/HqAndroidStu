package com.example.hqandroidstu

class HqTeacher(val className:String):HqPerson(){
    lateinit var _className:String
    //在这里做构造函数的初始化操作
    init {
        if (name.isEmpty()) {
            name = "刘亮"
        }
        //设置一个默认的班级
        if (className.isEmpty()) {
            _className = "3年级5班"
        }
    }

    override fun toString(): String {
        return "我叫$name, 是${_className}的班主任。"

    }
}