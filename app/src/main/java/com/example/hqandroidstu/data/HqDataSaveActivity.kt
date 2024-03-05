package com.example.hqandroidstu.data

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import com.example.hqandroidstu.R
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class HqDataSaveActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, HqDataSaveActivity::class.java)
            context.startActivity(intent)
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_data_save)
        setup()
    }
    private fun setup() {
        hideActionBar()
        initView()
    }

    private fun initView() {
        editText= findViewById(R.id.hqEditText)

        var content = loadTextFromFile()
        content = loadTextFromPreference()
        if (content.isNotEmpty()) {
            editText.setText(content)
            editText.setSelection(content.length)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
       if (editText.text.isNotEmpty()) {
           saveTextToFile(editText.text.toString())
           saveTextToPreference(editText.text.toString())
       }
    }

    private fun loadTextFromPreference():String {
        val preferences = getSharedPreferences("hq_preference",Context.MODE_PRIVATE)
        return preferences.getString("hq_text","").toString()
    }
    private fun saveTextToPreference(text:String){
        val preferences = getSharedPreferences("hq_preference",Context.MODE_PRIVATE)

//        val editor = preferences.edit()
//        editor.putString("hq_text",text)
//        editor.apply()

        //使用内置高阶函数来简化api调用
//        preferences.edit {
//            putString("hq_text",text)
//        }

        //使用自定义高阶函数，来简化api调用
        preferences.open {
            putString("hq_text",text)
        }
    }

    private fun loadTextFromFile():String {
        val content = StringBuilder()
        try {
           val input = openFileInput("hq_data")
           val reader = BufferedReader(InputStreamReader(input))
           reader.use {
               reader.forEachLine {
                   content.append(it)
               }
           }
       }catch (e:IOException) {
           e.printStackTrace()
       }
        return content.toString()
    }
    private fun saveTextToFile(text:String) {
        /*
        在文件创建的时候使用，注意这里指定的文件名 不可以包含路径，
        因为所有的文件都默认存储到/data/data/<your_package_name>/files/目录下;
        第二个参数是文件的操作模式，主要有MODE_PRIVATE和MODE_APPEND两种模式可选，
        默认是MODE_PRIVATE，表示当指定相同文件名的时候，所写入的内容将会覆盖原文件中的内容，
        而MODE_APPEND则表示如果该文件已存在，就往文件里面追加内容，不存在就创建新文件
        * */
        try {
            val output = openFileOutput("hq_data",Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            // use函数，这是Kotlin提供的一个内置扩展函数。它会保证在Lambda 表达式中的代码全部执行完之后自动将外层的流关闭，
            // 这样就不需要我们再编写一个finally 语句，手动去关闭流了，是一个非常好用的扩展函数
            writer.use {
                it.write(text)
            }
        } catch (e:IOException) {
            Toast.makeText(this,"write exception",Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}