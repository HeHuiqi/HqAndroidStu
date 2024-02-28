package com.example.hqandroidstu.uibase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.NewsContentFragBinding

class HqNewsContentFragment:Fragment() {
    private var rootBind: NewsContentFragBinding? = null
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return loadView(inflater,container)
//        return loadView1(inflater)

    }
    //加载视图方式1
    fun loadView(inflater: LayoutInflater,
                  container: ViewGroup?):View? {
        //加载视图方式1
        rootView = inflater.inflate(R.layout.news_content_frag,container,false)
        return rootView
    }
    fun refresh(title:String,content:String) {
        view?.let {
            val hqNewsContentLayout:LinearLayout = it.findViewById(R.id.hq_news_content_layout)
            val hqNewsTitle:TextView = it.findViewById(R.id.hq_news_title)
            val hqNewsContent:TextView = it.findViewById(R.id.hq_news_content)
            hqNewsContentLayout.visibility = View.VISIBLE
            hqNewsTitle.text = title
            hqNewsContent.text = content

        }
    }

    //加载视图方式2
    private fun loadView1(inflater: LayoutInflater):View? {
        //初始化视图绑定对象
        rootBind = NewsContentFragBinding.inflate(inflater)
        return rootBind?.root
    }
    fun refresh1(title:String,content:String) {
        rootBind?.hqNewsContentLayout?.visibility = View.VISIBLE
        rootBind?.hqNewsTitle?.text = title
        rootBind?.hqNewsContent?.text = content
    }
}