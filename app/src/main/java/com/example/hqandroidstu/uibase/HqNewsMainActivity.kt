package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R

class HqNewsMainActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context,) {
            val intent = Intent(context,HqNewsMainActivity::class.java)
            context.startActivity(intent)
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_news_main)
        setup()
    }
    private fun setup(){
        hideActionBar()
    }
}