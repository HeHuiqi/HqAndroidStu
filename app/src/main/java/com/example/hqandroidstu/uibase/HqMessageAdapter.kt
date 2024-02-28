package com.example.hqandroidstu.uibase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hqandroidstu.R

class HqMessageAdapter(val messageList: List<HqMessage>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    inner class LeftViewHolder(view:View):RecyclerView.ViewHolder(view) {
//        val leftMsgTv:TextView = view.findViewById(R.id.hq_message_left_tv)
//    }
//    inner class RightViewHolder(view: View):RecyclerView.ViewHolder(view) {
//        val rightMsgTv:TextView = view.findViewById(R.id.hq_message_right_tv)
//    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val msg = messageList[position]
        return msg.type
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == HqMessage.TYPE_RECEIVED) {
//            val  view = LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item,parent,false)
//            return LeftViewHolder(view)
//        }
//        val  view = LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item,parent,false)
//        return RightViewHolder(view)

        if (viewType == HqMessage.TYPE_RECEIVED) {
            val  view = LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item,parent,false)
            return HqLeftViewHolder(view)
        }
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item,parent,false)
        return HqRightViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messageList[position]
//        when (holder) {
//            is LeftViewHolder -> {
//                holder.leftMsgTv.text = msg.content
//            }
//            is RightViewHolder -> {
//                holder.rightMsgTv.text = msg.content
//            }
//            else -> {
//                throw IllegalArgumentException()
//            }
//        }

        //使用密封类优化
        when(holder) {
            is HqLeftViewHolder -> {
                holder.leftMsgTv.text = msg.content
            }
            is HqRightViewHolder -> {
                holder.rightMsgTv.text = msg.content
            }
        }
    }
}