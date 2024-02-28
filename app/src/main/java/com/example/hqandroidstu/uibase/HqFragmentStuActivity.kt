package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hqandroidstu.R

class HqFragmentStuActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqFragmentStuActivity::class.java)
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_fragment_stu)
        setup()
    }

    private fun initView() {


        val btn:Button = findViewById(R.id.hq_fragment_btn11)
        btn.setOnClickListener {
            replaceFragment(HqAnotherRightFragment())
            getFragment()
        }
//
        replaceFragment(HqRightFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.hq_fragment_layout1,fragment)
        //添加到返回栈中，可以层层返回
        transaction.addToBackStack("")
        transaction.commit()
    }
    private fun getFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.hq_left_fragment) as HqLeftFragment

//        Toast.makeText(this,"$fragment",Toast.LENGTH_SHORT).show()
        fragment.hqGetActivity()


    }
}