package com.example.hqandroidstu.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class HqDatabaseContentProvider(val  customContext: Context? = null) : ContentProvider() {


    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3
    private val authority = "com.example.hqandroidstu.provider"
    private var databaseHelper:HqDatabaseHelper? = null
    private val uriMatcher:UriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority,"book",bookDir)
        matcher.addURI(authority,"book/#",bookItem)
        matcher.addURI(authority,"category",categoryDir)
        matcher.addURI(authority,"category/#",categoryItem)
        matcher
    }

    init {
        context?.let {
            databaseHelper = HqDatabaseHelper(it,"BookStore.db",1)
        }
    }

    override fun getType(uri: Uri): String? {
        val dirPrefix ="vnd.android.cursor.dir"
        val itemPrefix = "vnd.android.cursor.item"
        val res = when(uriMatcher.match(uri)) {
            bookDir -> "$dirPrefix/vnd.$authority.book"
            bookItem -> "$itemPrefix/vnd.$authority.book"
            categoryDir -> "$dirPrefix/vnd.$authority.category"
            categoryItem -> "$itemPrefix/vnd.$authority.category"
            else -> null
        }
        return res
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        databaseHelper?.let {
            val db = it.writableDatabase
            val rows = when(uriMatcher.match(uri)) {
                bookDir -> {
                    db.delete("Book",selection,selectionArgs)

                }
                bookItem -> {
                    val bookId = uri.pathSegments[1]
                    db.delete("Book","id = ?", arrayOf(bookId))

                }
                categoryDir -> {
                    db.delete("Category",selection,selectionArgs)
                }
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    db.delete("Category","id = ?", arrayOf(categoryId))
                }
                else -> 0
            }
            return rows
        }
        return 0
    }
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        databaseHelper?.let {
           val db = it.writableDatabase
           val uriReturn = when(uriMatcher.match(uri)) {
               bookDir,bookItem -> {
                   val newBookId = db.insert("Book",null,values)
                   Uri.parse("content://$authority/book/$newBookId")
               }
               categoryDir,categoryItem -> {
                   val categoryId = db.insert("Category",null,values)
                   Uri.parse("content://$authority/category/$categoryId")
               }
               else -> null
           }
            return uriReturn
        }
        return null
    }

    override fun onCreate(): Boolean {
        val res = context?.let {
            databaseHelper = HqDatabaseHelper(it,"BookStore.db",1)
            true
        }
        Log.d("HqContentProvider", "onCreate: $res")
       return res ?: false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

//        customContext?.let {
//            databaseHelper = HqDatabaseHelper(it,"BookStore.db",1)
//        }


        val cursor = databaseHelper?.let {
            val db = it.writableDatabase
            val cursor = when (uriMatcher.match(uri)) {
                bookDir -> {
                    db.query("Book",projection,selection,selectionArgs,null,null,sortOrder)
                }
                bookItem -> {
                    val bookId = uri.pathSegments[1]
                    db.query("Book",projection,"id = ?", arrayOf(bookId),null,null,sortOrder)
                }
                categoryDir -> {
                    db.query("Category",projection,selection,selectionArgs,null,null,sortOrder)
                }
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    db.query("Category",projection,"id = ?", arrayOf(categoryId),null,null,sortOrder)
                }
                else -> {
                    null
                }

            }
            return cursor
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        databaseHelper?.let {
            val db = it.writableDatabase
            val rows = when(uriMatcher.match(uri)) {
                bookDir,bookItem -> {
                    val bookId = uri.pathSegments[1]
                    db.update("Book",values,"id = ?", arrayOf(bookId))
                }
                categoryDir,categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    db.update("Category",values,"id = ?", arrayOf(categoryId))
                }
                else -> 0
            }
            return rows
        }
        return 0
    }
}