package com.example.hqandroidstu.libs

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqLibsTestBinding
import com.example.hqandroidstu.showToast
import com.hhq.hqpermissionx.HqPermissionX

class HqLibsTestActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqLibsTestActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqLibsTestBinding by lazy {
        ActivityHqLibsTestBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.libTestBtn.setOnClickListener {
//            HqPermissionX.request(this,android.Manifest.permission.CALL_PHONE){ allGranted, deniedList ->
//                if (allGranted) {
//                    call()
//                } else {
//                    "你拒绝了${deniedList}权限".showToast()
//                }
//            }
            HqPermissionX.singleRequest(this,android.Manifest.permission.CALL_PHONE){ allGranted, deniedList ->
                if (allGranted) {
                    call()
                } else {
                    "你拒绝了${deniedList}权限".showToast()
                }
            }
        }
    }
    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}