package com.example.hqandroidstu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.hqandroidstu.ble.HqBluetoothStuActivity
import com.example.hqandroidstu.data.HqDBActivity
import com.example.hqandroidstu.data.HqDataSaveActivity
import com.example.hqandroidstu.datashare.HqDataShareActivity
import com.example.hqandroidstu.datashare.HqRuntimePermissionActivity
import com.example.hqandroidstu.datashare.HqVisitCustomContentProviderActivity
import com.example.hqandroidstu.dialog.HqDialogActivity
import com.example.hqandroidstu.file.HqFileManageActivity
import com.example.hqandroidstu.first.HqFirstActivity
import com.example.hqandroidstu.gaoji.HqGaojiActivity
import com.example.hqandroidstu.jetpack.HqJetpackStuActivity
import com.example.hqandroidstu.jetpack.HqRoomStuActivity
import com.example.hqandroidstu.jetpack.HqWorkManagerStuActivity
import com.example.hqandroidstu.kotlin.HqKotlinStuActivity
import com.example.hqandroidstu.libs.HqLibsTestActivity
import com.example.hqandroidstu.material.HqDrawerActivity
import com.example.hqandroidstu.material.HqMaterialActivity
import com.example.hqandroidstu.media.HqCameraPhotoActivity
import com.example.hqandroidstu.media.HqNotifyMainActivity
import com.example.hqandroidstu.media.HqPlayAudioActivity
import com.example.hqandroidstu.media.HqPlayVideoActivity
import com.example.hqandroidstu.network.HqDataParseActivity
import com.example.hqandroidstu.network.HqHttpRequestActivity
import com.example.hqandroidstu.network.HqRetrofitStuActivity
import com.example.hqandroidstu.network.HqWebViewStuActivity
import com.example.hqandroidstu.scan.HqCameraXActivity
import com.example.hqandroidstu.scan.HqScanCodeActivity
import com.example.hqandroidstu.scan.HqScanCodeSimpleActivity
import com.example.hqandroidstu.service.HqBroadcastActivityStu
import com.example.hqandroidstu.service.HqMultiThreadStuActivity
import com.example.hqandroidstu.service.HqServiceStuActivity
import com.example.hqandroidstu.uibase.HqCustomUIActivity
import com.example.hqandroidstu.uibase.HqCustomViewActivity
import com.example.hqandroidstu.uibase.HqFragmentStuActivity
import com.example.hqandroidstu.uibase.HqFragmentStuActivity2
import com.example.hqandroidstu.uibase.HqLayoutBaseActivity
import com.example.hqandroidstu.uibase.HqListViewActivity
import com.example.hqandroidstu.uibase.HqMessageActivity
import com.example.hqandroidstu.uibase.HqNewsMainActivity
import com.example.hqandroidstu.uibase.HqRecyclerViewActivity
import com.example.hqandroidstu.uibase.HqUIBaseActivity
import com.example.hqandroidstu.uibase.HqUICompetentActivity
import com.example.hqandroidstu.utils.hqStartActivity
import com.example.hqandroidstu.utils.showToast
import com.example.hqandroidstu.webview.HqWebViewActivity
import kotlin.math.log


fun  Activity.actionStart(context:Context,bundle: Bundle? = null) {
    val cls = this::class.java
    val intent = Intent(context, cls)
    if (bundle != null) {
        intent.putExtra("bundle",bundle)
    }
    context.startActivity(intent)
}

/*
* AppCompatActivity是 AndroidX中提供的一种向下兼容的Activity，
* 可以使Activity在不同系统版本中的功能保持一致性
* */
class MainActivity : AppCompatActivity() {
    private val  data:ArrayList<Activity> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate: execute")

        setup()
    }
    protected val mHandler = Handler(Looper.getMainLooper())
    private var mTimerRunnable = object : Runnable {
        override fun run() {
            Log.i("hq-m-timer", "run: ")
            mHandler.postDelayed(this, 2000)

        }
    }
    private fun setup() {
        setupData()
        setupUI()
//        initDebugView(this)

    }
    private fun initDebugView(c:Context) {

        if (!Settings.canDrawOverlays(c)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.setData(Uri.parse("package:$packageName"))
            startActivityForResult(intent,1)
            return
        }
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val debugView = LayoutInflater.from(c).inflate(R.layout.hq_deubg_view,null)
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT
//        )
        val params = LayoutParams().apply {
            //设置大小 自适应
            width = LayoutParams.WRAP_CONTENT
            height = LayoutParams.WRAP_CONTENT
            flags =
                LayoutParams.FLAG_NOT_TOUCH_MODAL or LayoutParams.FLAG_NOT_FOCUSABLE
        }
        windowManager.addView(debugView,params)
    }
    private fun setupData(){

        data.add(HqBluetoothStuActivity())
        data.add(HqFileManageActivity())
        data.add(HqFirstActivity())
        data.add(HqUIBaseActivity())
        data.add(HqLayoutBaseActivity())
        data.add(HqCustomUIActivity())
        data.add(HqListViewActivity())
        data.add(HqRecyclerViewActivity())
        data.add(HqMessageActivity())
        data.add(HqFragmentStuActivity())
        data.add(HqFragmentStuActivity2())
        data.add(HqNewsMainActivity())
        data.add(HqBroadcastActivityStu())
        data.add(HqDataSaveActivity())
        data.add(HqDBActivity())
        data.add(HqRuntimePermissionActivity())
        data.add(HqDataShareActivity())
        data.add(HqVisitCustomContentProviderActivity())
        data.add(HqNotifyMainActivity())
        data.add(HqCameraPhotoActivity())
        data.add(HqPlayAudioActivity())
        data.add(HqPlayVideoActivity())
        data.add(HqMultiThreadStuActivity())
        data.add(HqServiceStuActivity())
        data.add(HqWebViewStuActivity())
        data.add(HqHttpRequestActivity())
        data.add(HqDataParseActivity())
        data.add(HqRetrofitStuActivity())
        data.add(HqMaterialActivity())
        data.add(HqDrawerActivity())
        data.add(HqJetpackStuActivity())
        data.add(HqRoomStuActivity())
        data.add(HqWorkManagerStuActivity())
        data.add(HqGaojiActivity())
        data.add(HqLibsTestActivity())
        data.add(HqUICompetentActivity())
        data.add(HqKotlinStuActivity())
        data.add(HqCameraXActivity())
        data.add(HqScanCodeSimpleActivity())
        data.add(HqScanCodeActivity())
        data.add(HqDialogActivity())
        data.add(HqCustomViewActivity())
        data.add(HqWebViewActivity())

    }
    private fun setupUI(){
        val listView:ListView = findViewById(R.id.hq_main_list_view)
        val adapter = HqMainArrayAdapter(this,R.layout.main_list_item,data)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val ac  = data[position]
            ac.actionStart(this)
        }
        go()
    }
    private fun go(){
        val btn:Button = findViewById(R.id.go_btn)
        btn.setOnClickListener {
            hqStartActivity<HqWebViewActivity>(this)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initDebugView(this)

                } else {
                    "权限被拒绝".showToast()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mHandler.post(mTimerRunnable)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(mTimerRunnable)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            initDebugView(this)
        }
    }
}