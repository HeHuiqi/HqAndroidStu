package com.example.hqandroidstu.media

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqPlayVideoBinding

class HqPlayVideoActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqPlayVideoActivity::class.java)
            context.startActivity(intent)
        }
    }
    private val  rootBinding: ActivityHqPlayVideoBinding by lazy {
        ActivityHqPlayVideoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_play_video)
        setContentView(rootBinding.root)
        initView()
    }
    private fun initView() {
        initVideoView()
        rootBinding.hqPlayBtn.setOnClickListener {
            if (!rootBinding.hqVideoView.isPlaying) {
                rootBinding.hqVideoView.start()
            }
        }
        rootBinding.hqPauseBtn.setOnClickListener {
            if (rootBinding.hqVideoView.isPlaying) {
                rootBinding.hqVideoView.pause()
            }
        }
        rootBinding.hqReplayBtn.setOnClickListener {
            if (rootBinding.hqVideoView.isPlaying) {
                rootBinding.hqVideoView.resume()
            }
        }

        rootBinding.hqStopBtn.setOnClickListener {
            if (rootBinding.hqVideoView.isPlaying) {

                rootBinding.hqVideoView.stopPlayback()
                initVideoView()
            }
        }
    }
    private fun initVideoView(){
        //VideoView 不能播放assets下的资源文件，需要放在res/raw目录下
        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        rootBinding.hqVideoView.setVideoURI(uri)
    }

    override fun onDestroy() {
        super.onDestroy()
        rootBinding.hqVideoView.suspend()
    }
}