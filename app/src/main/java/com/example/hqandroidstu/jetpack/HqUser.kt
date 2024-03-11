package com.example.hqandroidstu.jetpack

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity 标记一个实体类，就相当创建一个表
@Entity
class HqUser(var firstName:String,var lastName:String,var age:Int) {

    //指定主键，自动生成
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    override fun toString(): String {
        return "{firstName:$firstName,lastName:$lastName,age:$age}"
    }

}