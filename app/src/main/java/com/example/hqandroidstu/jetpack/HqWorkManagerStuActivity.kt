package com.example.hqandroidstu.jetpack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqWorkManagerStuBinding
import com.example.hqandroidstu.showToast
import java.util.concurrent.TimeUnit

class HqWorkManagerStuActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqWorkManagerStuActivity::class.java)
               context.startActivity(intent)
        }
    }
  
    private val rootBinding:ActivityHqWorkManagerStuBinding by lazy {
        ActivityHqWorkManagerStuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.doWorkBtn.setOnClickListener {
            val tag = "HqWorkM"
            // setInitialDelay 设置多久后执行后台任务
            // 后台任务的doWork()方法中返回了Result.retry()， 那么是可以结合setBackoffCriteria()方法来重新执行任务的，最短不能少于10秒钟
            val  request = OneTimeWorkRequest.Builder(HqWorkManager::class.java)
                .setInitialDelay(5,TimeUnit.SECONDS)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
                .addTag(tag)
                .build()
            WorkManager.getInstance(this).enqueue(request)

            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(request.id)
                .observe(this){ workInfo ->
                    //任务执行是在子线程运行的，所以这里要回到UI线程更新UI
                    runOnUiThread {
                        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            "任务执行成功".showToast(this)

                        } else if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            "任务执行失败".showToast(this)
                        }
                    }

                }

            //添加tag可以根据tag来取消后台任务
//            WorkManager.getInstance(this).cancelAllWorkByTag(tag)

            //通过id来取消后台任务
//            WorkManager.getInstance(this).cancelWorkById(request.id)
            //取消所有后台任务
//            WorkManager.getInstance(this).cancelAllWork()
        }
    }
}