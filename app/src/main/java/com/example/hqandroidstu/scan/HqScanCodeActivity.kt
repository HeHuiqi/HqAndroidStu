package com.example.hqandroidstu.scan

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqScanCodeBinding
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

class HqScanCodeActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqScanCodeActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqScanCodeBinding by lazy {
        ActivityHqScanCodeBinding.inflate(layoutInflater)
    }
    private  val cameraExecutor by lazy {
        Executors.newSingleThreadExecutor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {

    }
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val  cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            //预览
            val preview = Preview.Builder()
                .build()
                .also {
                    // 从取景器中获取 Surface 提供程序，然后在预览上进行设置
                    it.setSurfaceProvider(rootBinding.cameraPreview.surfaceProvider)
                }
            val myImageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, HqImageAnalyzer{
                            image,bitmap ->
                            updateUI(bitmap)
                    })
                }
            //选择后置摄像头作为默认值
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this,cameraSelector,preview,myImageAnalyzer)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun  updateUI(bitmap:Bitmap){
        val image = InputImage.fromBitmap(bitmap, 0)
        runOnUiThread {
            rootBinding.showImageView.setImageBitmap(bitmap)
            HqScanCodeHandler.handleScanCode(image) { result, errInfo ->
                rootBinding.showScanCodeTv.text = result
            }
        }
    }
    private fun handleScanCode(){

        val  bitmap = BitmapFactory.decodeResource(resources,R.drawable.qrcode_test)
        val image = InputImage.fromBitmap(bitmap, 0)
        HqScanCodeHandler.handleScanCode(inputImage = image) { result, errInfo ->

        }

    }
}