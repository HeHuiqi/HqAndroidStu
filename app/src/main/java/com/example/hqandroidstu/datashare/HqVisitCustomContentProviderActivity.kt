package com.example.hqandroidstu.datashare

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hqandroidstu.R
import com.example.hqandroidstu.data.HqDatabaseContentProvider
import com.example.hqandroidstu.databinding.ActivityHqVisitCustomContentProviderBinding

class HqVisitCustomContentProviderActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqVisitCustomContentProviderActivity::class.java)
            context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqVisitCustomContentProviderBinding by lazy {
        ActivityHqVisitCustomContentProviderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_visit_custom_content_provider)
        setContentView(rootBinding.root)
        initView()
    }
    private fun initView() {
        rootBinding.hqQueryContentBtn.setOnClickListener {
            queryData()
        }
    }
    /*
    其他应用使用需要先配置如下：
```xml
<manifest>
<!-- 申请provider的权限   -->
    <uses-permission android:name="com.example.hqandroidstu.provider.READ_WRITE_PROVIDER" />
<!-- 配置查询的 provider authorities和提过者保持一致 -->
    <queries>
        <provider android:authorities="com.example.hqandroidstu.provider">
        </provider>
    </queries>

    <application>

    </application>
</manifest>
```
    * */
    private fun queryData() {
        val context = this
        val uri = Uri.parse("content://com.example.hqandroidstu.provider/book/1")
        contentResolver.query(uri,null,null,null,null)?.apply {
            val content = StringBuilder()
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow("name"))
                val price = getDouble(getColumnIndexOrThrow("price"))
                content.append("{name:$name,price:$price}")
            }

            Toast.makeText(context,content.toString(),Toast.LENGTH_SHORT).show()

            close()
        }
    }
}