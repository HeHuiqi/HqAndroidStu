package com.example.hqandroidstu.scan

import android.graphics.Bitmap
import android.graphics.Matrix

object HqImageUtil {
    fun rotateImage(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}