package com.example.hqandroidstu.scan

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Surface.ROTATION_0
import android.view.Surface.ROTATION_180
import android.view.Surface.ROTATION_90
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.hqandroidstu.databinding.ActivityHqCameraXactivityBinding
import com.google.mlkit.vision.common.InputImage
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

// Android 10 以上可以
// 搭载 Android 10 或更低版本的设备无法同时实现预览、图片拍摄和图片分析，这不适用于 Android Studio 的设备模拟器
private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {
        Log.i("LuminosityAnalyzer", "analyze: ")
        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()

        listener(luma)

        image.close()
    }
}


class HqCameraXActivity : AppCompatActivity() {
    companion object {

        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
        fun actionStart(context: Context) {
               val intent = Intent(context, HqCameraXActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqCameraXactivityBinding by lazy {
        ActivityHqCameraXactivityBinding.inflate(layoutInflater)
    }
    private var imageCapture: ImageCapture? = null

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private lateinit var cameraExecutor: ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listeners for take photo and video capture buttons
        rootBinding.imageCaptureButton.setOnClickListener { takePhoto() }
        rootBinding.videoCaptureButton.setOnClickListener { captureVideo() }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }
    private fun takePhoto() {
        // 获取可修改图像捕获用例的稳定参考
        val imageCapture = imageCapture ?: return
        // 创建带时间戳的名称和 MediaStore 条目
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValue = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,name)
            put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/CameraX-Image")
            }
        }
        // 创建包含文件+元数据的输出选项对象
        val outputOptions = ImageCapture.OutputFileOptions.Builder(contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValue)
            .build()
//        设置图像捕获监听器，在拍摄照片后触发
        imageCapture.takePicture(outputOptions,ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "拍照成功: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }

                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "拍照失败: ${exc.message}", exc)
                }

            })

    }

    @SuppressLint("CheckResult")
    private fun captureVideo() {
        val videoCapture = videoCapture ?:return
        rootBinding.videoCaptureButton.isEnabled = false
        val  curRecording = recording
        if (curRecording != null) {
            curRecording.stop()
            recording = null
            return
        }
        // 创建并开始一个新的录制会话
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val  contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,name)
            put(MediaStore.MediaColumns.MIME_TYPE,"video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH,"Movies/CameraX-Vide0")
            }
        }
        val mediaStoreOutputOptions = MediaStoreOutputOptions.Builder(contentResolver,MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()
        recording = videoCapture.output.prepareRecording(this,mediaStoreOutputOptions).apply {
            if (PermissionChecker.checkSelfPermission(this@HqCameraXActivity,android.Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED) {
                withAudioEnabled()
            }
        }.start(ContextCompat.getMainExecutor(this)) { recordEvent ->
            when(recordEvent) {
                is VideoRecordEvent.Start -> {
                    rootBinding.videoCaptureButton.apply {
                        text = "stop_capture"
                        isEnabled = true
                    }
                }
                is VideoRecordEvent.Finalize -> {
                    if (!recordEvent.hasError()) {
                        if (!recordEvent.hasError()) {
                            val msg = "Video capture succeeded: " +
                                    "${recordEvent.outputResults.outputUri}"
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
                                .show()
                            Log.d(TAG, msg)
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Video capture ends with error: " +
                                    "${recordEvent.error}")
                        }

                        rootBinding.videoCaptureButton.apply {
                            text = "start_capture"
                            isEnabled = true
                        }
                    }
                }
            }

         }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val  cameraProvider:ProcessCameraProvider = cameraProviderFuture.get()
            //预览
            val preview = Preview.Builder()
                .build()
                .also {
                    // 从取景器中获取 Surface 提供程序，然后在预览上进行设置
                    it.setSurfaceProvider(rootBinding.viewFinder.surfaceProvider)
                }
            val myImageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, HqImageAnalyzer{
                            image,bitmap ->
                        Log.i("MyImageAnalyzer", "startCamera: ${image} ")
                        runOnUiThread {
                            rootBinding.myImageView.setImageBitmap(bitmap)
                            HqScanCodeHandler.handleScanCode(image) { result, errInfo ->
                                rootBinding.scanCodeTv.text = result
                            }
                        }
                    })
                }
            //选择后置摄像头作为默认值
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this,cameraSelector,preview,myImageAnalyzer)
        },ContextCompat.getMainExecutor(this))
    }
    private fun startCamera1() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable{
            //用于将相机的生命周期与生命周期所有者绑定
             val  cameraProvider:ProcessCameraProvider = cameraProviderFuture.get()
            //预览
            val preview = Preview.Builder()
                .build()
                .also {
                    // 从取景器中获取 Surface 提供程序，然后在预览上进行设置
                    it.setSurfaceProvider(rootBinding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()

            //图像分析,会实时调用
            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }
            val myImageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, HqImageAnalyzer{
                        image,bitmap ->
                        Log.i("MyImageAnalyzer", "startCamera: ${image} ")
                        runOnUiThread {
                            rootBinding.myImageView.setImageBitmap(bitmap)
                            HqScanCodeHandler.handleScanCode(image) { result, errInfo ->  
                                rootBinding.scanCodeTv.text = result
                            }
                        }
                    })
                }

            val  recorder = Recorder.Builder().
            setQualitySelector(QualitySelector.from(Quality.HIGHEST, FallbackStrategy.higherQualityOrLowerThan(Quality.SD)))
                .build()
            videoCapture = VideoCapture.withOutput(recorder)
            //选择后置摄像头作为默认值
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // 重新绑定之前解除用例绑定
                cameraProvider.unbindAll()
                // 将用例绑定到相机
//                cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture,imageAnalyzer)
                cameraProvider.bindToLifecycle(this,cameraSelector,preview,myImageAnalyzer)
//                cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture,myImageAnalyzer)
                cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture,videoCapture)


            } catch (e:Exception) {
                Log.e(TAG, "用例绑定失败",e )
            }
        },ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}