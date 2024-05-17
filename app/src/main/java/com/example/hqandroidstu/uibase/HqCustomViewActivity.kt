package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqCustomViewBinding

class HqCustomViewActivity : AppCompatActivity() {
    
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqCustomViewActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqCustomViewBinding by lazy {
        ActivityHqCustomViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.hqFloatView.setOnButtonClick {
            Toast.makeText(this,"点击浮牛",Toast.LENGTH_SHORT).show()
        }
    }
}