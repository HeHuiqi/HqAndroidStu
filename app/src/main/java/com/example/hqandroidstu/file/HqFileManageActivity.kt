package com.example.hqandroidstu.file

import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hqandroidstu.databinding.ActivityHqFileManageBinding
import com.example.hqandroidstu.utils.showToast
import java.io.BufferedOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class HqFileManageActivity : AppCompatActivity() {
    private val rootBinding:ActivityHqFileManageBinding by lazy {
        ActivityHqFileManageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private val needAndroidPermission:String  by lazy {
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
    private val newApi = false
    private fun setup() {
        rootBinding.hqDateBtn.setOnClickListener {


            createFile()

        }
    }
    private fun createFile() {
        if (ContextCompat.checkSelfPermission(this,needAndroidPermission) == PackageManager.PERMISSION_GRANTED) {
            if (newApi) {
                test()
            } else {
                getSaveFile()
            }

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(needAndroidPermission),
                1
            )
        }
    }
    private fun nowDateToString():String {
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
        return  dateFormat.format(date)
    }

    private fun  test() {
        val ec = externalCacheDir?.absolutePath
        Log.i("hq-file", "ec: ${ec}")

        val fileName = "哈啰足迹_${nowDateToString()}.mp4"

        val values = ContentValues()
        values.put(MediaStore.Video.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies")
        try {
            val uri = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,values)

            Log.i("hq-file", "test: ${uri}")
            uri?.let {
//                val file = contentResolver.openFileDescriptor(uri, "w");
//                val  audioRecorder = MediaRecorder()
//                audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
//                audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//                audioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//                audioRecorder.setOutputFile(file?.fileDescriptor)
//                audioRecorder.setAudioChannels(1)
//                audioRecorder.prepare()
//                audioRecorder.start()
//                file?.close()

                val  file1 = it.path

                Log.i("hq-file", "pp: ${file1}")

                val outputStream = contentResolver.openOutputStream(uri)

                if (outputStream != null) {
                    val bos = BufferedOutputStream(outputStream)

                    val buffer = ByteArray(1024)
//                    val bis = BufferedInputStream(inputStream)
//                    var bytes = bis.read(buffer)
//                    while (bytes >= 0) {
//                        bos.write(buffer, 0, bytes)
//                        bos.flush()
//                        bytes = bis.read(buffer)
//                    }

                    bos.close()
                }
            }
        }catch (e:Exception) {
            Log.i("hq-file", e.toString())

        }


    }
    private fun getSaveFile(): String {
        var moviesDir: File? = null
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            moviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        }
        moviesDir = Environment.getExternalStorageDirectory()

        Log.i("hq-file", "getSaveFile1: $moviesDir")
        if (moviesDir == null || !moviesDir.exists()) {
            moviesDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
            if (moviesDir != null) {
                if (!moviesDir.exists()) {
                    moviesDir.mkdirs()
                }
            }
            Log.i("hq-file", "getSaveFile2: $moviesDir")
        }
        var fileName = "哈啰足迹_${nowDateToString()}.mp4"
//        fileName = "哈啰足迹_${nowDateToString()}"

//        val file = File.createTempFile(
//            fileName,  /* prefix */
//            ".mp4",  /* suffix */
//            moviesDir /* directory */
//        )
//        return  ""
        val  file = File(moviesDir,fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        "文件创建成功".showToast()
        var path = file.absolutePath
        Log.i("hq-file", "createNewFile: $path")
        return path
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1-> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (newApi) {
                        test()
                    } else {
                        getSaveFile()
                    }

                } else {
                    "用户拒绝了存储权限".showToast()
                }
            }
        }
    }
}