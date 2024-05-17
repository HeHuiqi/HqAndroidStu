package com.example.hqandroidstu.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import org.json.JSONObject

@SuppressLint("JavascriptInterface")
fun WebView.hqAddJavascriptInterface(jsInterface:Any) {
    this.addJavascriptInterface(jsInterface,"HqAndroidJsBridge")
}

/*
  * 安卓端：添加js调用接口实现类
  * webView.addJavascriptInterface(JsInterface(this,webView),"HqAndroidJsBridge")
  * js端：使用注册的的名字调用方法并传递参数
  * HqAndroidJsBridge.postMessage(JSON.stringify(passNativeData));
  * */
class HqJsInterface(val activity: Activity, private val webView: WebView) {

    private fun dealCallbackJsWithData(data:Map<String,Any>): String {
        val jsonObject = JSONObject(data)
        return jsonObject.toString()
    }

    //使用这个注解就可以让js 调用这个方法
    @JavascriptInterface
    public fun postMessage(msg:String) {
        Log.i("hq-JsInterface", "postMessage: $msg")
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
        val jsonObject = JSONObject(msg)
        val callbackId = jsonObject.getString("callbackId")
//        val params = jsonObject.getJSONObject("params")
//        Log.i("hq-JsInterface", "params: $params")

        if (callbackId.isNotEmpty()) {
            activity.runOnUiThread {
                val resp = mutableMapOf<String,Any>()
                resp["success"] = true
                resp["message"] = "ok"
                val data = dealCallbackJsWithData(resp)
                //直接调用js回调函数，不保证顺序
//                val js = "javascript:HqJsBridge.jsCallback('$data')"

                //通过 callbackId 准确回调对应js函数
                val js = "javascript:HqJsBridge.jsCallbacks['$callbackId']($data)"

                Log.i("hq-JsInterface", "js: $js")
                webView.evaluateJavascript(js) { }
            }
        }
    }
}