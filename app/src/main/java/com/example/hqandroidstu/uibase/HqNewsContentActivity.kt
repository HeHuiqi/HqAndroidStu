package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R

class HqNewsContentActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context,title:String,content:String) {
            val intent = Intent(context,HqNewsContentActivity::class.java)
            intent.putExtra("news_title",title)
            intent.putExtra("news_content",content)
            context.startActivity(intent)
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_news_content)
        setup()
    }
    private fun setup() {
        hideActionBar()
        initView()
    }
    private fun initView(){
        val title = intent.getStringExtra("news_title")
        val content = intent.getStringExtra("news_content")
        if (title != null && content != null) {
            val fragment = supportFragmentManager.findFragmentById(R.id.hq_news_content_frag) as HqNewsContentFragment
            fragment.refresh(title,content)


        }
    }
}