package com.example.hqandroidstu.datashare

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hqandroidstu.databinding.ActivityHqRuntimePermissionBinding

class HqRuntimePermissionActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqRuntimePermissionActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val rooBinding:ActivityHqRuntimePermissionBinding by lazy {
        ActivityHqRuntimePermissionBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_runtime_permission)
        setContentView(rooBinding.root)
        setup()
    }
    private fun setup() {
        initView()
    }
    private fun initView() {
        rooBinding.hqMakeCallBtn.setOnClickListener {
            //如果没有这个权限就申请，否则就拨打
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                /*
                * 必须在AndroidManifest.xml 中配置权限，申请才会有弹窗
                * 如下：
                * <!--  需要使用的硬件  -->
    <uses-feature
        android:name="android.hardware.telephony"
        android:required="false" />
<!--   直接拨打电话 -->
    <uses-permission android:name="android.permission.CALL_PHONE"/>

                * */
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),1)
            } else {
                call()
            }
        }
    }

    //监听权限申请的结果
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call()
                } else {
                    Toast.makeText(this,"你拒绝了打电话的权限",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun call(){
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        }catch (e:SecurityException) {
            e.printStackTrace()
        }
    }
}