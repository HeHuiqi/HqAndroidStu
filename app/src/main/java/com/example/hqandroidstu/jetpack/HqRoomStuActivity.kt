package com.example.hqandroidstu.jetpack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqRoomStuBinding
import kotlin.concurrent.thread

class HqRoomStuActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqRoomStuActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqRoomStuBinding by lazy {
        ActivityHqRoomStuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        val userDao = HqAppDatabase.getDatabase(this).userDao()

        val user1 = HqUser("Jack","he",30)
        val user2 = HqUser("Tom","Jin",34)
        rootBinding.addUserBtn.setOnClickListener {
            thread {
                 userDao.insertUser(user1)
                 userDao.insertUser(user2)
            }
        }
        rootBinding.getUserBtn.setOnClickListener {
            thread {
                val result = StringBuilder()
                for (user in userDao.loadAllUsers()) {
                    result.append(user.toString()+"\n")
                }
                resultContent(result.toString())
            }
        }


        rootBinding.updateUserBtn.setOnClickListener {
            thread {
                user1.age = 37
                //必须设置id，当操作的是一个实体参数时
                user1.id = 1
                userDao.updateUser(user1)
            }
        }
        rootBinding.deleteUserBtn.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("Jin")
            }
        }

        val bookDao = HqAppDatabase.getDatabase(this).bookDao()
        rootBinding.insertBookBtn.setOnClickListener {
            thread {
                val book = HqBook("android develop",123)
                val book2 = HqBook("web develop",200)
                bookDao.insertBook(book)
                bookDao.insertBook(book2)
            }


        }
        rootBinding.queryBookBtn.setOnClickListener {
            thread {
                val result = StringBuilder()
                for (book in bookDao.loadAllBooks()) {
                    result.append(book.toString()+"\n")
                }
                resultContent(result.toString())
            }
        }
    }
    private fun resultContent(content:String) {
        runOnUiThread {
            rootBinding.userContentTv.text = content
        }
    }

}