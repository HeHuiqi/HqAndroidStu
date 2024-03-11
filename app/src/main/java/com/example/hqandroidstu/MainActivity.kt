package com.example.hqandroidstu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.hqandroidstu.data.HqDBActivity
import com.example.hqandroidstu.data.HqDataSaveActivity
import com.example.hqandroidstu.databinding.ActivityMainBinding
import com.example.hqandroidstu.databinding.FirstLayoutBinding
import com.example.hqandroidstu.datashare.HqDataShareActivity
import com.example.hqandroidstu.datashare.HqRuntimePermissionActivity
import com.example.hqandroidstu.datashare.HqVisitCustomContentProviderActivity
import com.example.hqandroidstu.gaoji.HqGaojiActivity
import com.example.hqandroidstu.jetpack.HqJetpackStuActivity
import com.example.hqandroidstu.jetpack.HqRoomStuActivity
import com.example.hqandroidstu.jetpack.HqWorkManagerStuActivity
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
import com.example.hqandroidstu.service.HqBroadcastActivityStu
import com.example.hqandroidstu.service.HqMultiThreadStuActivity
import com.example.hqandroidstu.service.HqServiceStuActivity
import com.example.hqandroidstu.uibase.HqCustomUIActivity
import com.example.hqandroidstu.uibase.HqFragmentStuActivity
import com.example.hqandroidstu.uibase.HqFragmentStuActivity2
import com.example.hqandroidstu.uibase.HqLayoutBaseActivity
import com.example.hqandroidstu.uibase.HqListViewActivity
import com.example.hqandroidstu.uibase.HqMessageActivity
import com.example.hqandroidstu.uibase.HqNewsMainActivity
import com.example.hqandroidstu.uibase.HqRecyclerViewActivity
import com.example.hqandroidstu.uibase.HqUIBaseActivity

/*
* AppCompatActivity是 AndroidX中提供的一种向下兼容的Activity，
* 可以使Activity在不同系统版本中的功能保持一致性
* */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate: execute")

        setup()
    }
    private fun setup(){
        val btn:Button = findViewById(R.id.go_btn)
        btn.setOnClickListener {
//            pushNewActivity(this,HqFirstActivity::class.java)

//            HqUIBaseActivity.actionStart(this)
//            HqLayoutBaseActivity.actionStart(this)
//            HqCustomUIActivity.actionStart(this)
//            HqListViewActivity.actionStart(this)
//            HqRecyclerViewActivity.actionStart(this)
//            HqMessageActivity.actionStart(this)
//            HqFragmentStuActivity.actionStart(this)
//            HqFragmentStuActivity2.actionStart(this)
//            HqNewsMainActivity.actionStart(this)
//            HqBroadcastActivityStu.actionStart(this)
//            HqDataSaveActivity.actionStart(this)
//            HqDBActivity.actionStart(this)
//            HqRuntimePermissionActivity.actionStart(this)
//            HqDataShareActivity.actionStart(this)
//            HqVisitCustomContentProviderActivity.actionStart(this)
//            HqNotifyMainActivity.actionStart(this)
//            HqCameraPhotoActivity.actionStart(this)
//            HqPlayAudioActivity.actionStart(this)
//            HqPlayVideoActivity.actionStart(this)
//            HqMultiThreadStuActivity.actionStart(this)
//            HqServiceStuActivity.actionStart(this)
//            HqWebViewStuActivity.actionStart(this)
//            HqHttpRequestActivity.actionStart(this)
//            HqDataParseActivity.actionStart(this)
//            HqRetrofitStuActivity.actionStart(this)
//            HqMaterialActivity.actionStart(this)
//            HqDrawerActivity.actionStart(this)
//            HqJetpackStuActivity.actionStart(this)
//            HqRoomStuActivity.actionStart(this)
//            HqWorkManagerStuActivity.actionStart(this)
            HqGaojiActivity.actionStart(this)
            HqLibsTestActivity.actionStart(this)

        }
    }
    private fun pushNewActivity(context: Context, cls: Class<HqFirstActivity>){
        val newIntent = Intent()
        newIntent.setClass(context,cls)
        startActivity(newIntent)
    }
}