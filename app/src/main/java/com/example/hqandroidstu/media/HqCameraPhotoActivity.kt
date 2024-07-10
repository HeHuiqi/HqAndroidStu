package com.example.hqandroidstu.media

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.hqandroidstu.databinding.ActivityHqCameraPhotoBinding
import java.io.File
import java.io.IOException

class HqCameraPhotoActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqCameraPhotoActivity::class.java)
            context.startActivity(intent)
        }
    }
    private val TAG = "HqCameraPhotoActivity"
    private val rootBinding: ActivityHqCameraPhotoBinding by lazy {
        ActivityHqCameraPhotoBinding.inflate(layoutInflater)
    }
    lateinit var imageUri:Uri
    lateinit var outputImage:File
    private val takePhoto = 1
    private val fromAlbum = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_hq_camera_photo)
        setContentView(rootBinding.root)
        initView()
    }
    private fun initView(){
        rootBinding.hqCameraBtn.setOnClickListener {

           if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
               openCamera()
           } else {
               ActivityCompat.requestPermissions(this,
                  arrayOf(android.Manifest.permission.CAMERA) ,1)
               Toast.makeText(this,"没有相机权限",Toast.LENGTH_SHORT).show()
           }
        }
        rootBinding.hqPhotoBtn.setOnClickListener {

            openPhotos()

        }
    }
    private fun openPhotos(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent,fromAlbum)
    }
    private fun openCamera() {
        // 调用getExternalCacheDir() 方法可以得到这个目录，具体的路径是/sdcard/Android/data/<package name>/cache
        val path = externalCacheDir?.path +"/"+"output_image.jpg"
        Log.d(TAG, "openCamera: path=$path")
        outputImage = File(path)
        if (outputImage.exists()) {
            outputImage.delete()
        }
        val context = this

        try {
            outputImage.createNewFile()
            /*从Android 7.0系统开始，直接使用本地真实路径的Uri被认为是不安全的， 会抛出一个FileUriExposedException异常。而FileProvider则是一种特殊的 ContentProvider，它使用了和ContentProvider类似的机制来对数据进行保护，可以选择性 地将封装过的Uri共享给外部，从而提高了应用的安全性。*/
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //第二个参数和AndroidManifest.xml中的provider配置一致，必须先配置
                FileProvider.getUriForFile(context,"com.example.hqandroidstu.fileprovider",outputImage)
            } else {
                Uri.fromFile(outputImage)
            }
        }catch (e:IOException) {
            Log.d(TAG, "openCamera: ")
            e.printStackTrace()
        }

        // 启动相机程序
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //指定输出地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, takePhoto)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    // 将拍摄的照片显示出来
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    rootBinding.hqImageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->  
                        //// 将选择的图片显示
                        val bitmap =getBitmapFromUri(uri)
                        rootBinding.hqImageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
    private fun rotateIfRequired(bitmap: Bitmap) :Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL)
        return when(orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap,90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap,180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap,270)
            else -> bitmap
        }
    }
    private fun rotateBitmap(bitmap: Bitmap,degree:Int):Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
        bitmap.recycle() //回收对象
        return rotatedBitmap
    }
    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri,"r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this,"你拒绝了使用相机的权限",Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}