package com.hhq.hqpermissionx

import android.app.Activity
import androidx.fragment.app.FragmentActivity

object HqPermissionX {
    private const val TAG = "HqInvisibleFragment"
    fun request(activity: FragmentActivity, vararg permissions:String,callback:PermissionCallback) {
        val fragment = getInvisibleFragment(activity)
        fragment.requestNow(callback,*permissions)
    }
    private fun getInvisibleFragment(activity: FragmentActivity):HqInvisibleFragment {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as HqInvisibleFragment
        } else {
            val invisibleFragment = HqInvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        return fragment
    }
    fun singleRequest(activity: FragmentActivity,permission: String,cb:PermissionCallback) {
        val fragment = getInvisibleFragment(activity)
        fragment.singleRequest(cb,permission)
    }

}