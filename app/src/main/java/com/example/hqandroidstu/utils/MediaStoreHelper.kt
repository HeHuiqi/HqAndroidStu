package com.example.hqandroidstu.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.hqandroidstu.HqApplication
//import app.audio.filter.edit.base.App
//import com.theone.libs.netlib.utils.ToastUtils
import java.io.*
import java.util.*

/**
 * 媒体库助手
 */
object MediaStoreHelper {
    private const val TAG: String = "MediaStoreHelper"

    /**
     * 下载视频并添加到图库
     *
     * @param inputStream 视频输入流
     * @param displayName 文件名，包含后缀，如 xxx.mp4
     * @param mimeType    类型
     * @return 已添加的视频的Uri
     */
    fun downloadVideoToAlbum(
        context: Context,
        inputStream: InputStream?,
        displayName: String,
        mimeType: String?,
        relativePath: String,
        description: String?
    ): Uri? {
        var uri: Uri? = null
        try {
            val bis = BufferedInputStream(inputStream)
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis())
            values.put(MediaStore.Video.Media.DESCRIPTION, description)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
            } else {
                values.put(
                    MediaStore.MediaColumns.DATA,
                    Environment.getExternalStorageDirectory().path + "/" + relativePath + "/" + displayName
                )
            }
            uri =
                context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                val outputStream = context.contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    val bos = BufferedOutputStream(outputStream)
                    val buffer = ByteArray(1024)
                    var bytes = bis.read(buffer)
                    while (bytes >= 0) {
                        bos.write(buffer, 0, bytes)
                        bos.flush()
                        bytes = bis.read(buffer)
                    }
                    bos.close()
                }
            } else {
                uri = inputStream?.let {
                    saveMediaToMediaStore(
                        context, inputStream, displayName
                    )
                }
                Log.i("MyTestUri", "====> uri:$uri")
            }
            bis.close()
        } catch (e: IOException) {
            Log.e(TAG, "addVideoToAlbum: $e")
        }
        return uri
    }

    /**
     * 通过视频的名称判断媒体库是否存在同名的视频
     *
     * @param displayName 文件名，包含后缀，如 xxx.mp4
     * @return 有则返回Uri，无则返回空Uri
     */
    fun isVideoExist(context: Context, displayName: String): Uri {
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Video.Media._ID),
            MediaStore.Video.Media.DISPLAY_NAME + "=?",
            arrayOf(displayName),
            null
        ) ?: return Uri.EMPTY
        if (!cursor.moveToFirst()) {
            return Uri.EMPTY
        }
        val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
        val uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
        cursor.close()
        return uri
    }

    /**
     * 保存音频并添加到图库
     *
     * @param inputStream 视频输入流
     * @param displayName 文件名，包含后缀，如 xxx.mp4
     * @param mimeType    类型
     * @return 已添加的音频的Uri
     */
    fun downloadAudioToAlbum(
        context: Context,
        inputStream: InputStream?,
        displayName: String,
        mimeType: String?,
        relativePath: String,
        file: File
    ): Uri? {
        var uri: Uri? = null
        try {
            val bis = BufferedInputStream(inputStream)
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
                uri =
                    context.contentResolver.insert(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        values
                    )
            }
            if (uri != null) {
                val outputStream = context.contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    val bos = BufferedOutputStream(outputStream)
                    val buffer = ByteArray(1024)
                    var bytes = bis.read(buffer)
                    while (bytes >= 0) {
                        bos.write(buffer, 0, bytes)
                        bos.flush()
                        bytes = bis.read(buffer)
                    }
                    bos.close()
                }
            }

            Log.i("MeduiaApath", "===> 低版本获取的uri: $uri")
            bis.close()
        } catch (e: IOException) {
            Log.e(TAG, "addVideoToAlbum: $e")
        }
        return uri
    }

    fun updateFileName(
        context: Context,
        uri: Uri,
        name: String,
        relativePath:String,
        mimeType:String?=""
    ){
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        values.put(MediaStore.MediaColumns.DATA, relativePath)
        val update = context.contentResolver.update(uri, values, null, null)
        Log.i("showRenameFileDialog", "===> update: $uri , $update")
    }

    fun saveFileToSDCard(file: File, name: String, listener: (uri: Uri?) -> Unit) {
//        val pathParent = "FileUtil.getAudioPath()"
        val parentPath = file.parent

        val newFile = File("$parentPath/$name")
        if (newFile.exists()) {
            "文件名已存在".showToast()
            listener.invoke(null)
            return
        }
        val updateStatus = file.renameTo(newFile)

        if (updateStatus) {
            MediaScannerConnection.scanFile(
                HqApplication.context, arrayOf(newFile.absolutePath),
                null
            ) { path, uri ->
                Log.i(
                    "sendScanMedia",
                    "==通知媒体库结果==audioplay==> , uri: $uri  "
                )
                listener.invoke(uri)
            }
        } else {
            "文件保存失败".showToast()
        }
    }

    /**
     * 通过视频的名称判断媒体库是否存在同名的视频
     *
     * @param displayName 文件名，包含后缀，如 xxx.mp3
     * @return 有则返回Uri，无则返回空Uri
     */
    fun isAudioExist(context: Context, displayName: String): Uri {
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Audio.Media._ID),
            MediaStore.Audio.Media.DISPLAY_NAME + "=?",
            arrayOf(displayName),
            null
        ) ?: return Uri.EMPTY
        if (!cursor.moveToFirst()) {
            return Uri.EMPTY
        }
        val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
        val uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
        cursor.close()
        return uri
    }

    /**
     * 下载图片并添加到图库
     *
     * @param inputStream 图片输入流
     * @param displayName 文件名，包含后缀，如 xxx.png
     * @param mimeType    类型
     * @return 已添加的图片的Uri
     */
    fun downloadImageToAlbum(
        context: Context,
        inputStream: InputStream?,
        displayName: String,
        mimeType: String?,
        relativePath: String,
        description: String?
    ): Uri? {
        var uri: Uri? = null
        try {
            val bis = BufferedInputStream(inputStream)
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis())
            values.put(MediaStore.Images.Media.DESCRIPTION, description)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
            } else {
                values.put(
                    MediaStore.MediaColumns.DATA,
                    Environment.getExternalStorageDirectory().path + "/" + relativePath + "/" + displayName
                )
            }
            uri =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                val outputStream = context.contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    val bos = BufferedOutputStream(outputStream)
                    val buffer = ByteArray(1024)
                    var bytes = bis.read(buffer)
                    while (bytes >= 0) {
                        bos.write(buffer, 0, bytes)
                        bos.flush()
                        bytes = bis.read(buffer)
                    }
                    bos.close()
                }
            }
            bis.close()
        } catch (e: IOException) {
            Log.e(TAG, "addImageToAlbum: $e")
        }
        return uri
    }

    /**
     * 通过图片的名称判断媒体库是否存在同名的图片
     *
     * @param displayName 文件名，包含后缀，如 xxx.mp4
     * @return 有则返回Uri，无则返回空Uri
     */
    fun isImageExist(context: Context, displayName: String): Uri {
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DISPLAY_NAME + "=?",
            arrayOf(displayName),
            null
        ) ?: return Uri.EMPTY
        if (!cursor.moveToFirst()) {
            return Uri.EMPTY
        }
        val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
        val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
        cursor.close()
        return uri
    }

    /**
     * 通过视频的名称获取对应的Uri
     *
     * @param displayName 文件名，包含后缀，如 xxx.mp4
     * @return 找到则返回Uri，反之null
     */
    fun getUriByDisplayName(context: Context, displayName: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Video.Media._ID),
            MediaStore.Video.Media.DISPLAY_NAME + "=?",
            arrayOf(displayName),
            null
        ) ?: return null
        if (!cursor.moveToFirst()) {
            return null
        }
        val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
        val uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
        cursor.close()
        return uri
    }

    /**
     * 通过视频的描述获取对应的Uri
     *
     * @param description 下载时设置的描述名
     * @return 找到则返回List<Uri>，反之null
     */
    fun getLocalVideos(
        context: Context,
        description: String,
        page: Int,
        pageSize: Int
    ): MutableList<Uri> {
        val videos = mutableListOf<Uri>()
        val cursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val queryArgs = Bundle().apply {
                // 指定对结果进行排序的列列表（存储为 String[]）。当第一列值相同时，然后根据第二列值对记录进行排序，依此类推。
                putStringArray(
                    ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    arrayOf(MediaStore.MediaColumns.DATE_ADDED)
                )
                // 指定所需的排序顺序。这里是倒序。
                putInt(
                    ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                )
                // 分页
                putInt(ContentResolver.QUERY_ARG_LIMIT, pageSize)
                putInt(ContentResolver.QUERY_ARG_OFFSET, (page - 1) * pageSize)

                // 筛选条件
                putString(
                    ContentResolver.QUERY_ARG_SQL_SELECTION,
                    MediaStore.Video.Media.DESCRIPTION + "=?"
                )
                putStringArray(
                    ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS,
                    arrayOf(description)
                )
            }
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Video.Media._ID),
                queryArgs,
                null
            )
        } else {
            val sortOrder =
                "${MediaStore.MediaColumns.DATE_ADDED} DESC LIMIT $pageSize OFFSET ${(page - 1) * pageSize}"
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Video.Media._ID),
                MediaStore.Video.Media.DESCRIPTION + "=?",
                arrayOf(description),
                sortOrder
            )
        }

        cursor?.let {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                videos.add(uri)
            }
        }
        cursor?.close()
        return videos
    }

    /**
     * 通过图片的描述获取对应的Uri
     *
     * @param description 下载时设置的描述名
     * @return 找到则返回List<Uri>，反之null
     */
    fun getLocalImages(
        context: Context,
        description: String,
        page: Int,
        pageSize: Int
    ): MutableList<Uri> {
        val images = mutableListOf<Uri>()
        val cursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val queryArgs = Bundle().apply {
                // 指定对结果进行排序的列列表（存储为 String[]）。当第一列值相同时，然后根据第二列值对记录进行排序，依此类推。
                putStringArray(
                    ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    arrayOf(MediaStore.MediaColumns.DATE_ADDED)
                )
                // 指定所需的排序顺序。这里是倒序。
                putInt(
                    ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                )
                // 分页
                putInt(ContentResolver.QUERY_ARG_LIMIT, pageSize)
                putInt(ContentResolver.QUERY_ARG_OFFSET, (page - 1) * pageSize)

                // 筛选条件
                putString(
                    ContentResolver.QUERY_ARG_SQL_SELECTION,
                    MediaStore.MediaColumns.DISPLAY_NAME + " like ?"
                )
                putStringArray(
                    ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS,
                    arrayOf("${description}%")
                )
            }
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                queryArgs,
                null
            )
        } else {
            val sortOrder =
                "${MediaStore.MediaColumns.DATE_ADDED} DESC LIMIT $pageSize OFFSET ${(page - 1) * pageSize}"
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                MediaStore.MediaColumns.DISPLAY_NAME + " like ?",
                arrayOf("${description}%"),
                sortOrder
            )
        }

        cursor?.let {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                images.add(uri)
            }
        }
        cursor?.close()
        return images
    }

    @Throws(IOException::class)
    fun saveMediaToMediaStore(
        context: Context,
        inputStream: InputStream,
        fileName: String
    ): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues()
            contentValues.put(
                MediaStore.Video.Media.DISPLAY_NAME,
                fileName
            )
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis())
            val path = Environment.DIRECTORY_MOVIES + "/" + context.packageName
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, path)
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis())
            var uri = context.contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            if (uri == null) {
                contentValues.put(
                    MediaStore.Video.Media.DISPLAY_NAME,
                    fileName
                )
                uri = context.contentResolver.insert(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
            }
            val saveSuccess =
                uri?.let { itt ->
                    context.contentResolver.openOutputStream(itt)?.let {
                        saveFile(inputStream, it)
                    }
                }
            return saveSuccess?.let { if (it) uri else null }
        } else {
            val path =
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MOVIES
                ).absolutePath + "/" + context.packageName
            val fileDir = File(path)
            if (!fileDir.exists()) {
                fileDir.mkdir()
            }
            try {
                val file = File(fileDir, UUID.randomUUID().toString() + fileName)
                saveFile(inputStream, FileOutputStream(file))
                file?.let {
                    MediaScannerConnection.scanFile(
                        context, arrayOf(file.absolutePath),
                        null, null
                    )
                }
                return Uri.fromFile(file);
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    @Throws(IOException::class)
    private fun saveFile(inputStream: InputStream, outputStream: OutputStream): Boolean {
        val bin: BufferedInputStream?
        return try {
            bin = BufferedInputStream(inputStream)
            var size = 0
            val buf = ByteArray(size = 2048)
            while (bin.read(buf).also { size = it } != -1) {
                outputStream.write(buf, 0, size)
            }
            outputStream.flush()
            true
        } catch (e: IOException) {
            false
        } finally {
            try {
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {
                return false
            }
        }
    }
}