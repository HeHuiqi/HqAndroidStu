package com.example.hqandroidstu.webview

import android.R.id
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.JsResult
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hqandroidstu.R


class HqWebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    companion object {
//        fun actionStart(context: Context) {
//               val intent = Intent(context, HqWebViewActivity::class.java)
//               context.startActivity(intent)
//        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_web_view)
        setup()
    }
    fun method1() {
        method2()
    }
    fun method2() {

        method3()
    }
    fun method3() {
        Log.i("hq-webview", "method3: ")
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun setup() {
        webView = findViewById(R.id.hq_webview)
        webView.settings.javaScriptEnabled = true
        webView.hqAddJavascriptInterface(HqJsInterface(this,webView))
        //加载本地html
        webView.loadUrl("file:///android_asset/index.html");

        val btn:Button = findViewById(R.id.hqCallJsBtn)
        btn.setOnClickListener {
            method1()
            // 调用JS中的方法1
//            webView.loadUrl("javascript:jsHello()")
            //调用JS中的方法2
            val js = "javascript:HqJsBridge.nativeCallJs('我是Android')"

            webView.evaluateJavascript(js,object :ValueCallback<String>{
                override fun onReceiveValue(value: String?) {
                }
            })

        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.i("HqWebViewActivity1","开始加载网页");
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.i("HqWebViewActivity1","网页加载完成");

            }
            // 链接跳转都会走这个方法
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // view.loadUrl(url);// 强制在当前 WebView 中加载 url
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        val context = this
        webView.webChromeClient = object :WebChromeClient() {
            //js 调用 alert 方法的处理
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                val b = AlertDialog.Builder(context)
                b.setTitle(message)
                b.setPositiveButton(android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, which -> result!!.confirm() })
                b.setCancelable(false)
                b.create().show()
                return true
            }
        }

    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }


}