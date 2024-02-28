package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hqandroidstu.R

class HqUIBaseActivity : AppCompatActivity(),View.OnClickListener {
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqUIBaseActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_uibase)
        setup()
    }

    private fun setup() {
        val btn1:Button = findViewById(R.id.base_btn1)
        btn1.setOnClickListener(this)

        val btn2:Button = findViewById(R.id.base_btn2)
        btn2.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.base_btn1 -> {
                val editText:EditText = findViewById(R.id.base_editText)
                if (editText.text.toString().isEmpty()) {
                    Toast.makeText(this,editText.text.toString(),Toast.LENGTH_SHORT).show()
                }

                val  imgv:ImageView = findViewById(R.id.base_imageView)
                //修改图片
                imgv.setImageResource(R.drawable.img2)

                val progressBar:ProgressBar = findViewById(R.id.base_progressbar)
                if (progressBar.visibility == View.VISIBLE) {
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.VISIBLE
                }
            }
            R.id.base_btn2 -> {
                val progressBar:ProgressBar = findViewById(R.id.base_progressbar2)
                progressBar.progress = progressBar.progress + 10
                AlertDialog.Builder(this).apply {
                    setTitle("友情提示")
                    setMessage("下雨天，请不要出门了！")
                    setPositiveButton("Ok"){dialog,which->

                    }
                    setNegativeButton("Cancel"){dialog,which ->

                    }
                    show()
                }


                }



            }


        }


}