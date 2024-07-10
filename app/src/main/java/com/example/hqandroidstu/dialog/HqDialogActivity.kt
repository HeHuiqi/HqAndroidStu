package com.example.hqandroidstu.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqDialogBinding
import com.example.hqandroidstu.utils.showToast
import com.google.android.material.snackbar.Snackbar


class HqDialogActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqDialogActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqDialogBinding by lazy {
        ActivityHqDialogBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.showBtn.setOnClickListener { view1 ->
            dialogTest()

        }
    }
    private fun dialogTest(){
        val dialog = HqDialog(this)
        dialog.setDialogInfo("调拨提示","该车辆不在门店，该车辆不在门店,确认调拨？")
        dialog.show()
        dialog.onCheckBoxClick = { isCheck ->
            "isCheck:$isCheck".showToast(this)
        }
        dialog.onCancelClick = {
            "onCancelClick".showToast(this)
        }
        dialog.onConfirmClick = {
            "onConfirmClick".showToast(this)
        }

    }
    fun testCode(view1:View){
        val view = LayoutInflater.from(this).inflate(R.layout.hq_toast_layout, findViewById(R.id.hqToastLayout))
        val tv:TextView = view.findViewById(R.id.hqToastTv)
        tv.text = "请安装高德地图"

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(view)
        toast.show()

        val snackbar = Snackbar.make(view1,"请安装高德地图",Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
//            val  parmas =
        snackbar.show()
    }
}