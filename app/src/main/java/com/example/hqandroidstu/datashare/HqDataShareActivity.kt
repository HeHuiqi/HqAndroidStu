package com.example.hqandroidstu.datashare

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hqandroidstu.R
import com.example.hqandroidstu.data.HqDataSaveActivity
import com.example.hqandroidstu.databinding.ActivityHqDataSaveBinding
import com.example.hqandroidstu.databinding.ActivityHqDataShareBinding

class HqDataShareActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val  intent = Intent(context,HqDataShareActivity::class.java)
            context.startActivity(intent)
        }
    }
    private val roodBinding:ActivityHqDataShareBinding by lazy {
        ActivityHqDataShareBinding.inflate(layoutInflater)
    }
    private val contactList = ArrayList<String>()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hq_data_share)
        setContentView(roodBinding.root)
        initView()
    }
    private fun initView() {
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,contactList)
        roodBinding.hqContactListView.adapter = arrayAdapter
        requestContactPermission()
    }

    private fun requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),1)
        } else {
            readContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this,"你拒绝读取联系人权限",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun readContacts() {
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)?.apply {
            while (moveToNext()) {
                //读取姓名
                val name = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                //读取手机号
                val phoneNumber = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactList.add("姓名：$name\n手机号：$phoneNumber\n")
            }
            arrayAdapter.notifyDataSetChanged()
            close()
        }
    }
}