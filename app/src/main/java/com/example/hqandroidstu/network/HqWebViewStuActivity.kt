package com.example.hqandroidstu.network

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqWebviewStuBinding

class HqWebViewStuActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqWebViewStuActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqWebviewStuBinding by lazy {
        ActivityHqWebviewStuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        // 让 WebView支持JavaScript脚本。
        rootBinding.hqWebView.settings.javaScriptEnabled = true
        //传入 了一个WebViewClient的实例。这段代码的作用是，当需要从一个网页跳转到另一个网页时， 我们希望目标网页仍然在当前WebView中显示，而不是打开系统浏览器
        rootBinding.hqWebView.webViewClient = WebViewClient()
        rootBinding.hqWebView.loadUrl("https://www.baidu.com")
    }
    
}