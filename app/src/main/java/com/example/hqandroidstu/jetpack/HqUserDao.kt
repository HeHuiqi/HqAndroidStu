package com.example.hqandroidstu.jetpack

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//标记这个接口就是一个数据库操作对象，只能时接口，内部会通过proxy类实现接口方法
@Dao
interface HqUserDao {

    @Insert
    fun insertUser(user: HqUser):Long

    @Update
    fun updateUser(user: HqUser)

    @Query("select * from HqUser")
    fun loadAllUsers():List<HqUser>

    // 使用非实体类参数来增删改数据，那么就必须编写 SQL语句了，且必须使用@Query注解
    @Query("select * from HqUser where age > :age")
    fun loadUserOlderThan(age:Int):List<HqUser>

    @Delete
    fun deleteUser(user: HqUser)

    // 使用非实体类参数来增删改数据，那么就必须编写 SQL语句了,且必须使用@Query注解
    @Query("delete from HqUser where lastName = :lastName")
    fun deleteUserByLastName(lastName:String):Int
}