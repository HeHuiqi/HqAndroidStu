package com.example.hqandroidstu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.hqandroidstu.databinding.ActivityMainBinding
import com.example.hqandroidstu.databinding.FirstLayoutBinding
import com.example.hqandroidstu.service.HqBroadcastActivityStu
import com.example.hqandroidstu.uibase.HqCustomUIActivity
import com.example.hqandroidstu.uibase.HqFragmentStuActivity
import com.example.hqandroidstu.uibase.HqFragmentStuActivity2
import com.example.hqandroidstu.uibase.HqLayoutBaseActivity
import com.example.hqandroidstu.uibase.HqListViewActivity
import com.example.hqandroidstu.uibase.HqMessageActivity
import com.example.hqandroidstu.uibase.HqNewsMainActivity
import com.example.hqandroidstu.uibase.HqRecyclerViewActivity
import com.example.hqandroidstu.uibase.HqUIBaseActivity

/*
* AppCompatActivity是 AndroidX中提供的一种向下兼容的Activity，
* 可以使Activity在不同系统版本中的功能保持一致性
* */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate: execute")
        setup()
    }
    private fun setup(){
        val btn:Button = findViewById(R.id.go_btn)
        btn.setOnClickListener {
//            pushNewActivity(this,HqFirstActivity::class.java)

//            HqUIBaseActivity.actionStart(this)
//            HqLayoutBaseActivity.actionStart(this)
//            HqCustomUIActivity.actionStart(this)
//            HqListViewActivity.actionStart(this)
//            HqRecyclerViewActivity.actionStart(this)
//            HqMessageActivity.actionStart(this)
//            HqFragmentStuActivity.actionStart(this)
//            HqFragmentStuActivity2.actionStart(this)
//            HqNewsMainActivity.actionStart(this)
            HqBroadcastActivityStu.actionStart(this)

        }
    }
    private fun pushNewActivity(context: Context, cls: Class<HqFirstActivity>){
        val newIntent = Intent()
        newIntent.setClass(context,cls)
        startActivity(newIntent)
    }
}