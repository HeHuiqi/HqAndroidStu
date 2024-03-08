package com.example.hqandroidstu.network

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqRetrofitStuBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object  ServiceCreator{
    private const val BASE_URL = "https://api.github.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass:Class<T>):T = retrofit.create(serviceClass)
    //范型实化
    inline fun <reified T> create():T = create(T::class.java)

}

class HqRetrofitStuActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqRetrofitStuActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqRetrofitStuBinding by lazy {
        ActivityHqRetrofitStuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun setup() {
        rootBinding.hqRetrofitSendBtn.setOnClickListener {
//            sendRequest()

            //协程要放在协程域下调用
            GlobalScope.launch {
                sendRequest2()
            }
        }
    }


    private fun sendRequest(){
        //自动解析为模型             .addConverterFactory(GsonConverterFactory.create())
        //模型中的字段和json中的key要保持一致
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val service: HqGitHubService = retrofit.create(HqGitHubService::class.java)

        //简化调用
//        val service = ServiceCreator.create(HqGitHubService::class.java)

        val service = ServiceCreator.create<HqGitHubService>()

        val repoCalls =  service.listRepos("hehuiqi")

        //当发起请求的时候，Retrofit会自动在内部开启子线 程，当数据回调到Callback中之后，Retrofit又会自动切换回主线程
        repoCalls.enqueue(object :retrofit2.Callback<List<HqGitHubRepo>>{

            override fun onResponse(
                call: Call<List<HqGitHubRepo>>,
                response: Response<List<HqGitHubRepo>>
            ) {
                val list = response.body()
                val result = StringBuilder()
                if (list != null) {
                    for (repo in list) {
                        val repoInfo = "{id:${repo.id},node_id:${repo.node_id},name:${repo.name}}\n"
                        result.append(repoInfo)
                    }
                }
                //会自动回到UI线程，所以这里可以直接更新UI
                rootBinding.hqContentTv.text = result.toString()
            }

            override fun onFailure(call: Call<List<HqGitHubRepo>>, t: Throwable) {

            }
        })
    }

    suspend fun <T> Call<T>.await():T {
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })

        }
    }

    //会建立一个协程
   private suspend fun sendRequest2(){
        try {
            val service = ServiceCreator.create<HqGitHubService>()
            val repoCalls = service.listRepos("hehuiqi")
            val list = repoCalls.await()
            val result = StringBuilder()
            for (repo in list) {
                val repoInfo = "{id:${repo.id},node_id:${repo.node_id},name:${repo.name}}\n"
                result.append(repoInfo)
            }
            //所以这里要回到UI线程更新UI
            runOnUiThread{
                rootBinding.hqContentTv.text = result.toString()
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}