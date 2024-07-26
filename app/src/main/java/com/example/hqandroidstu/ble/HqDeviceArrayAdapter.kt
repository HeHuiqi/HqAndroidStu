package com.example.hqandroidstu.ble

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.hqandroidstu.R


class HqDeviceArrayAdapter(private val  ctx:Context,private val resId:Int,private val devices:ArrayList<HqBleDevice>):ArrayAdapter<HqBleDevice>(ctx,resId,devices) {
    //定义内部类
    inner class HqViewHolder(val textView: TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder:HqViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(ctx).inflate(resId,parent,false)
            val textView: TextView = view.findViewById(R.id.deviceTv)
            viewHolder = HqViewHolder(textView)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as HqViewHolder
        }

        val dv = getItem(position)
        viewHolder.textView.text = dv?.deviceName;

        return view
    }
}