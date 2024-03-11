package com.example.hqandroidstu.gaoji

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqDataShowBinding

class HqDataShowActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context,person: HqPerson) {
               val intent = Intent(context, HqDataShowActivity::class.java)
                intent.putExtra("person_data",person)
               context.startActivity(intent)
        }
        fun actionStart2(context: Context,student: HqStudent) {
            val intent = Intent(context, HqDataShowActivity::class.java)
            intent.putExtra("student_data",student)
            context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqDataShowBinding by lazy {
        ActivityHqDataShowBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        //获取序列化的数据
        val person = intent.getSerializableExtra("person_data") as? HqPerson
        var name = ""
        person?.let {
           name = it.name
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val student = intent.getParcelableExtra("student_data",HqStudent::class.java)
            student?.let {
                name = it.name

            }
        }
        rootBinding.showDataTv.text = name

    }
}