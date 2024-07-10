package com.example.hqandroidstu.first

import android.os.Bundle
import android.widget.Button
import com.example.hqandroidstu.HqActivityManager
import com.example.hqandroidstu.HqBaseActivity
import com.example.hqandroidstu.R

class HqThirdActivity : HqBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_third)
        setup()
    }
    private fun setup() {
        val btn3:Button = findViewById(R.id.button3)
        btn3.setOnClickListener {
            HqActivityManager.finishAll()
        }
    }
}