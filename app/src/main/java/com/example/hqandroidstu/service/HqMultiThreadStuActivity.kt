package com.example.hqandroidstu.service



import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqMultiThreadStuBinding
import kotlin.concurrent.thread

//启动任务 如下
// DownloadTask().execute()
class DownloadTask:AsyncTask<Unit,Int,Boolean>(){

    private fun downloadFile():Int {
        return 30
    }

    override fun onPreExecute() {
        //做一些准备工作，可以处理UI操作，初始化
    }
    override fun doInBackground(vararg params: Unit?): Boolean {
        //开始执行任务
        while (true) {
            val downloadPercent = downloadFile()
            //发布任务进度
            publishProgress(downloadPercent)
            if (downloadPercent >= 100) {
                break
            }
        }
        return true
    }

    override fun onProgressUpdate(vararg values: Int?) {
        //更新进度，可以处理UI操作
    }

    override fun onPostExecute(result: Boolean?) {
        //最后的结果，可以处理UI操作
    }


}

class HqMultiThreadStuActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqMultiThreadStuActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqMultiThreadStuBinding by lazy {
        ActivityHqMultiThreadStuBinding.inflate(layoutInflater)

    }
    private val updateText = 1
    private val handler = object :Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                updateText -> {

                    rootBinding.hqContentTv.text = msg.data.getString("data")
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.hqChangeTextBtn.setOnClickListener {
            thread {
                //在子线程中使用handler来通知主线程刷新UI
                val bundle = Bundle()
                bundle.putString("data","Hello World")
                val msg = Message()
                msg.what = updateText
                msg.data = bundle

                handler.sendMessage(msg)
            }
        }
    }

}