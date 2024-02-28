package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R

class HqFragmentStuActivity2 : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqFragmentStuActivity2::class.java)
            context.startActivity(intent)
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    private fun setup(){
        hideActionBar()
        initView()
    }
    private fun initView() {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //会根据设备屏幕大小来自动加载不同的布局
        // 手机加载 layout/activity_hq_fragment_stu2.xml
        // 平板加载 layout-large/activity_hq_fragment_stu2.xml
        // large就是一个限定 符，那些屏幕被认为是large的设备就会自动加载layout-large文件夹下的布局
        setContentView(R.layout.activity_hq_fragment_stu2)
        setup()
    }

}