package com.example.hqandroidstu.material

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqDrawerBinding
import com.example.hqandroidstu.showSnackBar
import com.example.hqandroidstu.showToast
import com.example.hqandroidstu.uibase.HqFruit
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class HqDrawerActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqDrawerActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqDrawerBinding by lazy {
        ActivityHqDrawerBinding.inflate(layoutInflater)
    }
    private val fruitList:ArrayList<HqFruit> = ArrayList()
    private lateinit var adapter4: HqFruitAdapter4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }

    private fun setup(){
        initActionBar()
        initView()
        initFruitList()
        initRecyclerView()
        setupRefresh()

    }
    private fun initView(){
        //默认选中项
        rootBinding.navView.setCheckedItem(R.id.navCall)
        //选中navView的item事件
        rootBinding.navView.setNavigationItemSelectedListener {
            //关闭抽屉
            rootBinding.hqDrawerLayout.closeDrawers()
            true
        }
        rootBinding.floatBtn.setOnClickListener { view->
//            Toast.makeText(this,"点击了悬浮按钮",Toast.LENGTH_SHORT).show()
//            Snackbar.make(view,"确认删除吗？",Snackbar.LENGTH_SHORT)
//                .setAction("确认"){
//                    Toast.makeText(this,"好的",Toast.LENGTH_SHORT).show()
//                }
//                .show()

            //使用扩展简化调用
            view.showSnackBar("确认删除吗？", actionText = "ddd") {
                "好的".showToast(this)
            }


        }
    }
    private fun initActionBar() {
        val toolbar = rootBinding.toolbar
        setSupportActionBar(toolbar)
        toolbar.title = "自定义"
        supportActionBar?.let {
            //home是actionbar系统自带的
            it.setDisplayHomeAsUpEnabled(true)
            //设置home图标，它默认的图标是一个返回的箭头
            it.setHomeAsUpIndicator(R.drawable.grape_pic)
        }
    }
    private fun initFruitList(){
        repeat(2){
            fruitList.add(HqFruit("Apple", R.drawable.apple_pic))
            fruitList.add(HqFruit("Banana", R.drawable.banana_pic))
            fruitList.add(HqFruit("Orange", R.drawable.orange_pic))
            fruitList.add(HqFruit("Watermelon", R.drawable.watermelon_pic))
            fruitList.add(HqFruit("Pear", R.drawable.pear_pic))
            fruitList.add(HqFruit("Grape", R.drawable.grape_pic))
            fruitList.add(HqFruit("Pineapple", R.drawable.pineapple_pic))
            fruitList.add(HqFruit("Strawberry", R.drawable.strawberry_pic))
            fruitList.add(HqFruit("Cherry", R.drawable.cherry_pic))
            fruitList.add(HqFruit("Mango", R.drawable.mango_pic))
        }
    }

    private fun initRecyclerView(){
        val layoutManager = GridLayoutManager(this,2)
        rootBinding.hqRecyclerView.layoutManager = layoutManager
        adapter4 = HqFruitAdapter4(this,fruitList)
        rootBinding.hqRecyclerView.adapter = adapter4
        adapter4.itemClick = { fruit ->
            HqFruitActivity.actionStart(this,fruit)
        }
    }
    private fun setupRefresh() {
        rootBinding.hqSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        rootBinding.hqSwipeRefreshLayout.setOnRefreshListener {
            refreshFruits()
        }
    }
    private fun refreshFruits() {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruitList()
                adapter4.notifyDataSetChanged()
                rootBinding.hqSwipeRefreshLayout.isRefreshing = false

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //加载菜单项
        menuInflater.inflate(R.menu.toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //点击菜单项
        when(item.itemId) {
            //点击home打开抽屉
            android.R.id.home -> {
                rootBinding.hqDrawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.backup -> {
                Toast.makeText(this,"点击备份", Toast.LENGTH_SHORT).show()
            }
            R.id.delete -> {
                Toast.makeText(this,"点击删除", Toast.LENGTH_SHORT).show()
            }
            R.id.settings -> {
                Toast.makeText(this,"点击设置", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}