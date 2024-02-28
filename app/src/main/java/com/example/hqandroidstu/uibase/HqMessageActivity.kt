package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hqandroidstu.R

class HqMessageActivity : AppCompatActivity(),View.OnClickListener {
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqMessageActivity::class.java)
            context.startActivity(intent)
        }
    }
    private lateinit var adapter:HqMessageAdapter
    private val msgList = ArrayList<HqMessage>()
    private lateinit var recyclerView:RecyclerView
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_message)
        setup()
    }
    private fun setup() {
        hideActionBar()
        initMsg()
        initView()

    }
    private fun initMsg(){
        val msg = HqMessage("你好，伙计！",HqMessage.TYPE_RECEIVED)
        msgList.add(msg)

        val msg2 = HqMessage("你好，最近如何",HqMessage.TYPE_SEND)
        msgList.add(msg2)

        val msg3 = HqMessage("我还好，你呢?",HqMessage.TYPE_RECEIVED)
        msgList.add(msg3)
    }
    private fun initView() {
        //如果没有初始化就初始化
        if (!::adapter.isInitialized) {
            adapter = HqMessageAdapter(msgList)
        }


        recyclerView = findViewById(R.id.hq_recycler_view_message)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val sendBtn:Button = findViewById(R.id.hq_message_send_button)
        sendBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.hq_message_send_button -> {
                val textView:TextView = findViewById(R.id.hq_message_editText)
                val content = textView.text.toString()
                if (content.isNotEmpty()) {
                    val msg = HqMessage(content,HqMessage.TYPE_SEND)
                    msgList.add(msg)
                    val msg2 = HqMessage("OK",HqMessage.TYPE_RECEIVED)
                    msgList.add(msg2)
                    //通知适配器数据更新，刷新页面
                    adapter.notifyItemInserted(msgList.size-1)
                    //滚动到最后一行
                    recyclerView.scrollToPosition(msgList.size-1)
                    textView.text = ""

                }
            }
        }
    }
}