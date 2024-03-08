package com.example.hqandroidstu.material

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hqandroidstu.R
import com.example.hqandroidstu.uibase.HqFruit

class HqFruitAdapter4(val context: Context,val fruitList: List<HqFruit>):RecyclerView.Adapter<HqFruitAdapter4.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImageView: ImageView = view.findViewById(R.id.fruitImageView)
        val fruitNameTv: TextView = view.findViewById(R.id.fruitNameTv)
    }

    var itemClick = {_:HqFruit -> Unit}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.fruit_item4,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            itemClick(fruitList[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitNameTv.text = fruit.name
        //load()方法加载图片，可以是一个URL地址，也可以是一个本地路径，或者是一个资 源id，最后调用into()方法将图片设置到具体某一个ImageView中就可以了
        Glide.with(context).load(fruit.imgId).into(holder.fruitImageView)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}