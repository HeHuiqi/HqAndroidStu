package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R

class HqLayoutBaseActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqLayoutBaseActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_layout_base)
        setContentView(R.layout.activity_relative_layout_base)
    }
}