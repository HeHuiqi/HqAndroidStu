package com.example.hqandroidstu.uibase

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.hqandroidstu.R

class HqFruitAdapter(activity: Activity,val resId:Int,data:List<HqFruit>):ArrayAdapter<HqFruit>(activity,resId,data) {
    //定义内部类
    inner class HqViewHolder(val imageView: ImageView,val  textView: TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View
        val viewHolder:HqViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resId,parent,false)
            val imageView:ImageView = view.findViewById(R.id.fruitImageView)
            val textView:TextView = view.findViewById(R.id.fruitTv)
            viewHolder = HqViewHolder(imageView,textView)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as HqViewHolder
        }

        val fruit:HqFruit? = getItem(position)
        if (fruit != null) {
            viewHolder.imageView.setImageResource(fruit.imgId)
            viewHolder.textView.text = fruit.name
        }
//        return super.getView(position, convertView, parent)
        return view
    }
}