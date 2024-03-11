package com.hhq.hqpermissionx

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
typealias PermissionCallback = (Boolean, List<String>) -> Unit
class HqInvisibleFragment:Fragment() {
    private var callback : PermissionCallback? = null
    private val deniedList = ArrayList<String>()
    private var singPermission = ""

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        deniedList.add(singPermission)
        if (isGranted) {
            // PERMISSION GRANTED
            callback?.let {
                it(isGranted,deniedList)
            }
        } else {
            callback?.let {
                it(false,deniedList)
            }
        }
    }

    fun requestNow(cb:PermissionCallback,vararg permissions: String) {
        callback = cb
        if (context != null) {
           val it = context as Activity
            ActivityCompat.requestPermissions(it, permissions,1)
        } else {
            requestPermissions(permissions,1)
        }

    }

    fun singleRequest(cb:PermissionCallback, permission: String) {
        callback = cb
        singPermission = permission
        requestPermissionLauncher.launch(permission)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index,result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let {
                it(allGranted,deniedList)
            }
        }

    }
}