package com.example.hqandroidstu.jetpack

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HqBookDao {

    @Insert
    fun insertBook(book: HqBook):Long

    @Query("select * from HqBook")
    fun loadAllBooks():List<HqBook>

}