package com.example.hqandroidstu

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class HqMainArrayAdapter(private val customContext:Context, private val resId:Int, private val activities:ArrayList<Activity>):ArrayAdapter<Activity>(customContext,resId,activities) {
    //定义内部类
    inner class HqViewHolder(val textView: TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder:HqViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(customContext).inflate(resId,parent,false)
            val textView:TextView = view.findViewById(R.id.hq_main_title_tv)
            viewHolder = HqViewHolder(textView)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as HqViewHolder
        }

        val cls = getItem(position)
        cls?.let {
            val title: String? = it::class.java.simpleName
            if (title != null) {
                viewHolder.textView.text = title
            }
        }
        return view
    }
}