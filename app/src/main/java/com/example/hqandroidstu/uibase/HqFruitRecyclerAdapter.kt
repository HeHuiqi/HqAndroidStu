package com.example.hqandroidstu.uibase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hqandroidstu.R

class HqFruitRecyclerAdapter(val fruitList: List<HqFruit>,val resId:Int):RecyclerView.Adapter<HqFruitRecyclerAdapter.HqViewHolder>() {
    inner class HqViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val imageView:ImageView = view.findViewById(R.id.fruitImageView)
        val textView:TextView = view.findViewById(R.id.fruitTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resId,parent,false)
        val viewHolder =  HqViewHolder(view)

        // itemView 是保护子视图的父视图
        viewHolder.itemView.setOnClickListener {
            //必须在点击时获取位置，否则会崩溃，不能写在外部
            val fruit = fruitList[viewHolder.adapterPosition]
            Toast.makeText(parent.context,"点击整个Item:${fruit.name}",Toast.LENGTH_SHORT).show()

        }
        viewHolder.imageView.setOnClickListener {
            //必须在点击时获取位置，否则会崩溃，不能写在外部
            val fruit = fruitList[viewHolder.adapterPosition]
            Toast.makeText(parent.context,"点击了图片:${fruit.name}",Toast.LENGTH_SHORT).show()
        }

        return viewHolder
    }
    override fun onBindViewHolder(holder: HqViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.imageView.setImageResource(fruit.imgId)
        holder.textView.text = fruit.name
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

}