package com.example.hqandroidstu.uibase

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hqandroidstu.R

class HqNewsTitleFragment:Fragment() {
    private var isTwoPane = false

    inner class NewsAdapter(private val  newsList:List<HqNews>):RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
        inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
            val newsTile:TextView = view.findViewById(R.id.hqNewsTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =LayoutInflater.from(parent.context).inflate(R.layout.news_title_item,parent,false)
            val holder = ViewHolder(view)
            holder.itemView.setOnClickListener {
                val news = newsList[holder.adapterPosition]
                if (isTwoPane) {
                    activity?.let {
                        val  fragment = it.supportFragmentManager.findFragmentById(R.id.hq_news_content_frag) as HqNewsContentFragment
                        fragment.refresh(news.title,news.content)
                    }

                } else {
                    HqNewsContentActivity.actionStart(parent.context,news.title,news.content)
                }
            }
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsTile.text = news.title
        }

        override fun getItemCount(): Int {
            return newsList.size
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_title_frag,container,false)
    }

    // 重载String 类的 * 乘法运算符
    operator fun String.times(n: Int): String {
        val builder = StringBuilder()
        repeat(n) {
            builder.append(this)
        }
        return builder.toString()
    }

    private fun getRandomLengthString(str: String): String {
//        val n = (1..20).random()
//        val builder = StringBuilder()
//        repeat(n) {
//            builder.append(str)
//        }
//        return builder.toString()
        return str * 20
    }
    private fun getNews(): List<HqNews> {
        val newsList = ArrayList<HqNews>()
        for (i in 1..50) {
            val news =
                HqNews("This is news title $i", getRandomLengthString("This is news content $i. "))
            newsList.add(news)
        }
        return newsList
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            isTwoPane = it.findViewById<View>(R.id.hq_news_content_layout) != null
            val  newsList = getNews()
            val layoutManager = LinearLayoutManager(it)
            val recyclerView:RecyclerView = it.findViewById(R.id.hq_news_title_RecyclerView)
            val adapter = NewsAdapter(newsList)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            if (isTwoPane) {
                val  fragment = it.supportFragmentManager.findFragmentById(R.id.hq_news_content_frag) as HqNewsContentFragment
                val  news = newsList.first()
                fragment.refresh(news.title,news.content)
            }
        }
    }

}