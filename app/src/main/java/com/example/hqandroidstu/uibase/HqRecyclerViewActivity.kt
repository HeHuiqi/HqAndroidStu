package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hqandroidstu.R

class HqRecyclerViewActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqRecyclerViewActivity::class.java)
            context.startActivity(intent)
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    private val fruitList:ArrayList<HqFruit> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_recycler_view)
        setup()
    }
    private fun setup() {
        hideActionBar()
        initFruitList()

        val recyclerView:RecyclerView = findViewById(R.id.hq_recycler_view)

//        verticalList(recyclerView)
//        horizontalList(recyclerView)
//        gridList(recyclerView)
        staggeredGridList(recyclerView)
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
    private fun initFruitList2(){
        repeat(2){
            fruitList.add(HqFruit(getRandomLengthString("Apple"),
                R.drawable.apple_pic))
            fruitList.add(HqFruit(getRandomLengthString("Banana"),
                R.drawable.banana_pic))
            fruitList.add(HqFruit(getRandomLengthString("Orange"),
                R.drawable.orange_pic))
            fruitList.add(HqFruit(getRandomLengthString("Watermelon"),
                R.drawable.watermelon_pic))
            fruitList.add(HqFruit(getRandomLengthString("Pear"),
                R.drawable.pear_pic))
            fruitList.add(HqFruit(getRandomLengthString("Grape"),
                R.drawable.grape_pic))
            fruitList.add(HqFruit(getRandomLengthString("Pineapple"),
                R.drawable.pineapple_pic))
            fruitList.add(HqFruit(getRandomLengthString("Strawberry"),
                R.drawable.strawberry_pic))
            fruitList.add(HqFruit(getRandomLengthString("Cherry"),
                R.drawable.cherry_pic))
            fruitList.add(HqFruit(getRandomLengthString("Mango"),
                R.drawable.mango_pic))
        }
    }
    private fun getRandomLengthString(str: String): String {
        val n =  (1..20).random()
        val builder = StringBuilder()
        repeat(n) {
            builder.append(str)
        }
        return builder.toString()
    }
    private fun verticalList(recyclerView: RecyclerView){
        //LayoutManager用于指定RecyclerView的布局方式，这里使用的 LinearLayoutManager是线性布局的意思
        val  verticalScroll = true
        /*
        * 除了LinearLayoutManager之外，
        * RecyclerView还给我们提供了GridLayoutManager和 StaggeredGridLayoutManager这两种内置的布局排列方式。
        * GridLayoutManager可以用于 实现网格布局，
        * StaggeredGridLayoutManager可以用于实现瀑布流布局
        * */
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = HqFruitRecyclerAdapter(fruitList,R.layout.fruit_item)
        recyclerView.adapter = adapter
    }
    private fun horizontalList(recyclerView: RecyclerView){
        //LayoutManager用于指定RecyclerView的布局方式，这里使用的 LinearLayoutManager是线性布局的意思
        val layoutManager = LinearLayoutManager(this)
        //这种方向，这里是水平滚动
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        val adapter = HqFruitRecyclerAdapter(fruitList,R.layout.fruit_item2)
        recyclerView.adapter = adapter
    }

    //网格列表
    private fun gridList(recyclerView: RecyclerView){
        val layoutManager = GridLayoutManager(this,3)
        recyclerView.layoutManager = layoutManager
        val adapter = HqFruitRecyclerAdapter(fruitList,R.layout.fruit_item)
        recyclerView.adapter = adapter
    }
    //瀑布流列表
    private fun staggeredGridList(recyclerView: RecyclerView){
        fruitList.clear()
        initFruitList2()
        val layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        val adapter = HqFruitRecyclerAdapter(fruitList,R.layout.fruit_item3)
        recyclerView.adapter = adapter
    }



}