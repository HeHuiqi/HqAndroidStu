package com.example.hqandroidstu.network

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqHttpRequestBinding
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.sql.Connection
import kotlin.concurrent.thread

class HqHttpRequestActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqHttpRequestActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqHttpRequestBinding by lazy {
        ActivityHqHttpRequestBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.hqSendReqBtn.setOnClickListener {
//            sendRequest()
            okhttpSendRequest()
        }
        rootBinding.hqSendReqBtn2.setOnClickListener {
//            HqHttpUtil().sendHttpRequest("https://www.baidu.com",object :HttpCallbackListener{
//                override fun onFinish(response: String) {
//                    updateUI(response)
//
//                }
//
//                override fun onError(e: Exception) {
//
//                }
//            })

            HqHttpUtil().sendOkHttpRequest("https://www.baidu.com",object :okhttp3.Callback{
                override fun onResponse(call: Call, response: Response) {
                    response.body?.let {
                        updateUI(it.string())
                    }
                }

                override fun onFailure(call: Call, e: IOException) {

                }
            })
        }
    }

    private fun okhttpSendRequest(){
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://www.baidu.com")
                    .build()
                val response = client.newCall(request).execute()
                val data = response.body?.string()
                if (data != null) {
                    updateUI(data)
                }
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun sendRequest() {
        //开启子线程请求
        thread {
            var connection: HttpURLConnection? = null
            try {
                val resp = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000

                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        resp.append(it)
                    }
                }
                updateUI(resp.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }
    private fun postRequest(connection: HttpURLConnection?){
        connection?.let {
            it.requestMethod = "POST"
            it.doInput = true
            it.doInput = true
            val output = DataOutputStream(it.outputStream)
            output.writeBytes("username=admin&password=123456")
            output.flush()
            output.close()

        }
    }
    private fun updateUI(resp:String) {
        //回到主线程更新UI
        runOnUiThread {
            rootBinding.hqResponseTv.text = resp
        }
    }
}