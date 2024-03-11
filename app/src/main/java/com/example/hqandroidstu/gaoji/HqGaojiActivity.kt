package com.example.hqandroidstu.gaoji

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqGaojiBinding

class HqGaojiActivity : AppCompatActivity() {
   companion object {
       fun actionStart(context: Context) {
              val intent = Intent(context, HqGaojiActivity::class.java)
              context.startActivity(intent)
       }
   }
    private val rootBinding:ActivityHqGaojiBinding by lazy {
        ActivityHqGaojiBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.passDataBtn.setOnClickListener {
            val person = HqPerson()
            person.age = 1
            person.name = "Tom"
            passData(person)
        }
        rootBinding.passDataBtn2.setOnClickListener {
            val student = HqStudent()
            student.age = 2
            student.name = "Jack"
            passData2(student)
        }

    }
    private fun passData(person: HqPerson) {
        HqDataShowActivity.actionStart(this,person)
    }
    private fun passData2(student: HqStudent) {
        HqDataShowActivity.actionStart2(this,student)
    }
}