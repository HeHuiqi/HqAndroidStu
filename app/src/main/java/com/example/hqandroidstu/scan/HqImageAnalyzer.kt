package com.example.hqandroidstu.scan

import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage


class HqImageAnalyzer(val  analyzeComplete:(image: InputImage, bitmap: Bitmap) -> Unit) : ImageAnalysis.Analyzer {
    override fun analyze(imageProxy: ImageProxy) {
//        val rotationDegrees: Int = imageProxy.imageInfo.rotationDegrees
        val  bitmap = imageProxy.toBitmap()
        val newBitmap = HqImageUtil.rotateImage(bitmap,90.0f)
        val mediaImage = InputImage.fromBitmap(newBitmap, 0)
        analyzeComplete(mediaImage,newBitmap)
        imageProxy.close()

    }
}