package com.example.hqandroidstu.media

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqPlayAudioBinding

class HqPlayAudioActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqPlayAudioActivity::class.java)
            context.startActivity(intent)
        }
    }
    private var  isStop = false
    private val rootBinding:ActivityHqPlayAudioBinding by lazy {
        ActivityHqPlayAudioBinding.inflate(layoutInflater)
    }
    private val mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_play_audio)
        setContentView(rootBinding.root)
        initView()
    }
    private fun initView() {
        initMediaPlayer()
        rootBinding.hqPlayBtn.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }
        rootBinding.hqPauseBtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }
        rootBinding.hqStopBtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                // https://www.jianshu.com/p/55afaa0a96f7
                //停止方式1
                //重置后需要重写初始化播放器
//                mediaPlayer.reset()
//                initMediaPlayer()

                //停止方式2
                mediaPlayer.stop()
                mediaPlayer.prepare()

            }
        }
    }
    private fun initMediaPlayer() {
        val assetManager = assets
        val fd = assetManager.openFd("music.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}