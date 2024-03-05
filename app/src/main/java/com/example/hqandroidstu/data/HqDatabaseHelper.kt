package com.example.hqandroidstu.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class HqDatabaseHelper(val context: Context,name:String,version:Int):SQLiteOpenHelper(context,name,null,version) {
    private val createBook = "create table if not exists Book (" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)"
    private val createCategory = "create table if not exists Category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)"
    override fun onCreate(db: SQLiteDatabase?) {
        //创建的数据默认在/data/data/<your_package_name>/databases/目录下
        db?.execSQL(createBook)
        db?.execSQL(createCategory)
//        Toast.makeText(context,"创建数据库成功",Toast.LENGTH_SHORT).show()
    }

//    通过创建HqDatabaseHelper修改不同的version来升级数据库，会调用onUpgrade()方法，version只能比上一次大，否则会崩溃
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //通过升级，删除已有表，再新建表，来更新数据库表
//        db?.execSQL("drop table if exists Book")
//        db?.execSQL("drop table if exists Category")
//        onCreate(db)
        //通过版本判断来增加新表或字段，这是推荐的做法
        if (oldVersion <= 1) {
            db?.execSQL(createCategory)
        }
    }
}