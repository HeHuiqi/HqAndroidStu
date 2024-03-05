package com.example.hqandroidstu.data

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.core.content.contentValuesOf
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqDbactivityBinding
import java.lang.StringBuilder
import kotlin.Exception

class HqDBActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, HqDBActivity::class.java)
            context.startActivity(intent)
        }
    }


    private val rootBinding:ActivityHqDbactivityBinding by lazy {
        ActivityHqDbactivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_dbactivity)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup(){
        initView()
        queryData()
        transactionOp()

    }
    private fun initView(){
        val dbHelper = HqDatabaseHelper(this,"BookStore.db",1)
        rootBinding.hqCreateDatabaseBtn.setOnClickListener {
            //通过修改不同的version来升级数据库，会调用onUpgrade()方法，version只能比上一次大，否则会崩溃
            dbHelper.writableDatabase
        }
        rootBinding.hqInsertBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            val value1 = ContentValues().apply {
                //设置数据
                put("name","安卓开发")
                put("author","奇风")
                put("pages",500)
                put("price",108.5)
            }
            db.insert("Book",null,value1)

            val value2 = ContentValues().apply {
                put("name","iOS开发")
                put("author","何其正")
                put("pages",600)
                put("price",110.5)
            }
            db.insert("Book",null,value2)
            //通过sql直接执行插入
            db.execSQL("insert into Book (name,author,pages,price) values(?,?,?,?)", arrayOf("Web开发","QQ",800,80.8))

            //通过系统扩展api简化调用
            val value3 = contentValuesOf("name" to "嵌入式开发","author" to "QiGe","pages" to 2048,"price" to 203.9)
            db.insert("Book",null,value3)

            //通过自定义cvOf来简化api调用
            val value4 = cvOf("name" to "硬件设计","author" to "QiGe","pages" to 4096,"price" to 109.3)
            db.insert("Book",null,value4)

        }
        rootBinding.hqUpdateBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            val value = ContentValues().apply {
                put("price",98)
            }
            db.update("Book",value,"author = ?", arrayOf("何其正"))
            //通过sql直接执行更新
            db.execSQL("update Book set price = ? where name = ?", arrayOf("100","安卓开发"))

        }
        rootBinding.hqDeleteBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book","pages > ?", arrayOf("500"))
            db.execSQL("delete from Book where author = ?", arrayOf("奇风"))
        }

    }
    private fun queryData() {
        val dbHelper = HqDatabaseHelper(this,"BookStore.db",1)
        rootBinding.hqQueryBtn.setOnClickListener {
            val db = dbHelper.readableDatabase
//            val cursor = db.query("Book",null,null,null,null,null,null)
            //通过sql直接查询,注意何其他操作的方法的区别，db.execSQL()
            val cursor = db.rawQuery("select * from Book",null)


            val result = StringBuilder()
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val author = cursor.getString(cursor.getColumnIndexOrThrow("author"))
                    val pages = cursor.getInt(cursor.getColumnIndexOrThrow("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                    result.append("{name:$name,author:$author,pages:$pages,price:$price}\n")

                }while (cursor.moveToNext())
            }

            cursor.close()
            rootBinding.hqDataTV.text = result.toString()
        }
    }
    private fun transactionOp (){
        val dbHelper = HqDatabaseHelper(this,"BookStore.db",1)
        rootBinding.hqTransitionBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            //开启事务
            db.beginTransaction()

            try {
                db.execSQL("insert into Book (name,author,pages,price) values(?,?,?,?)", arrayOf("Server 开发","qiqi",800,93.2) )
                db.execSQL("update Book set pages = ? where name = ?", arrayOf(1234,"Server 开发"))
                //所以操作执行完成后，要设置事务执行成功
                db.setTransactionSuccessful()

            }catch (e:Exception) {
                e.printStackTrace()
            } finally {
                //结束事务
                db.endTransaction()
            }


        }
    }
}