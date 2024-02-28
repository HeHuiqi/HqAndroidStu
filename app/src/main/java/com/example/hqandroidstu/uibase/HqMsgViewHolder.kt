package com.example.hqandroidstu.uibase

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hqandroidstu.R

//定义一个密封类
sealed class HqMsgViewHolder(view: View):RecyclerView.ViewHolder(view)

class HqLeftViewHolder(view: View): HqMsgViewHolder(view) {
    val leftMsgTv:TextView = view.findViewById(R.id.hq_message_left_tv)
}
class HqRightViewHolder(view: View): HqMsgViewHolder(view) {
    val rightMsgTv:TextView = view.findViewById(R.id.hq_message_right_tv)
}