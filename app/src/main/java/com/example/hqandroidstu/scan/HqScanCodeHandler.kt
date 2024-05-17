package com.example.hqandroidstu.scan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.hqandroidstu.R
import com.example.hqandroidstu.showToast
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

object HqScanCodeHandler {
    fun handleScanCode(inputImage: InputImage,handleComplete:(result:String,errInfo:String?) -> Unit){
        /*
        代码 128 (FORMAT_CODE_128)
        代码 39 (FORMAT_CODE_39)
        代码 93 (FORMAT_CODE_93)
        科达巴 (FORMAT_CODABAR)
        EAN-13（FORMAT_EAN_13）
        EAN-8（FORMAT_EAN_8）
        ITF（FORMAT_ITF）
        UPC-A (FORMAT_UPC_A)
        UPC-E（FORMAT_UPC_E）
        二维码 (FORMAT_QR_CODE)
        PDF417 (FORMAT_PDF417)
        阿兹特克语 (FORMAT_AZTEC)
        数据矩阵 (FORMAT_DATA_MATRIX)
        * */
        val barcodeOptions = BarcodeScannerOptions.Builder().setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC)
            .build()
        val scanner = BarcodeScanning.getClient(barcodeOptions)
        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->

                for (barcode in barcodes) {
                    val info = "type:${barcode.valueType} value:${barcode.rawValue}"
                    handleComplete(info,null)

                }

            }.addOnFailureListener {
                handleComplete("","识别失败")

            }
    }

}