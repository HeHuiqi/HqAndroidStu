package com.example.hqandroidstu.network

import android.util.Log
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

interface HttpCallbackListener {
    fun onFinish(response:String)
    fun onError(e:Exception)
}
/*
使用:
        HqHttpUtil().sendHttpRequest("https://www.baidu.com",object : HttpCallbackListener {
            override fun onFinish(response: String) {

            }

            override fun onError(e: Exception) {

            }
        })

* */
class HqHttpUtil {
    fun sendHttpRequest(address:String,listener: HttpCallbackListener){
        thread {
            var connection:HttpURLConnection? = null
            try {
                val response = StringBuffer()
                val url = URL(address)
                connection = url.openConnection() as HttpURLConnection
                connection.readTimeout = 8000
                connection.connectTimeout = 8000
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                listener.onFinish(response.toString())

            }catch (e:Exception) {
                e.printStackTrace()
                listener.onError(e)
            } finally {
                connection?.disconnect()
            }

        }
    }
    fun sendOkHttpRequest(address: String,callback:okhttp3.Callback){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback)

    }
    suspend fun request(address: String): String {
        return suspendCoroutine { continuation ->

            sendHttpRequest(address,object : HttpCallbackListener{
                override fun onFinish(response: String) {
                    continuation.resume(response)
                }

                override fun onError(e: Exception) {
                    continuation.resumeWithException(e)
                }
            })
        }
    }
    fun testRequest(){
        sendHttpRequest("https://www.baidu.com",object : HttpCallbackListener{
            override fun onFinish(response: String) {
                Log.d("HqHttpUtil", "onFinish: ${response}")
            }

            override fun onError(e: Exception) {

            }
        })
    }
    fun testOkRequest(){
        sendOkHttpRequest("https://www.baidu.com",object :okhttp3.Callback{
            override fun onResponse(call: Call, response: Response) {
                Log.d("HqHttpUtil", "onResponse: ${response.body?.string()}")

            }

            override fun onFailure(call: Call, e: IOException) {

            }
        })
    }

    suspend fun testReq(){
        try {
            val resp = request("https://www.baidu.com")
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}