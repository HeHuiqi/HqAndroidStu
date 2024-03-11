package com.example.hqandroidstu.jetpack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HqBook(var name:String,var pages:Int) {
    @PrimaryKey(true)
    var id:Long = 0

    override fun toString(): String {
        return "{name:$name,page:$pages}"
    }
}