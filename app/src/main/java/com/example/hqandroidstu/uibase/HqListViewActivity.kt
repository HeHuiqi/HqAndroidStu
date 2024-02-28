package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.hqandroidstu.R

class HqListViewActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqListViewActivity::class.java)
            context.startActivity(intent)
        }
    }
    private val data = listOf("Apple", "Banana", "Orange", "Watermelon",
        "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
        "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
        "Pineapple", "Strawberry", "Cherry", "Mango")

    private val fruitList:ArrayList<HqFruit> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_list_view)
        setup()
    }

    private fun setup() {
        hideActionBar()

        initFruitList()


        val listView:ListView = findViewById(R.id.hq_list_view)
//        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data)
//        listView.adapter = adapter

        val adapter = HqFruitAdapter(this,R.layout.fruit_item,fruitList)
        listView.adapter = adapter

        //设置列表点击监听
        listView.setOnItemClickListener { parent, view, position, id ->
            val fruit = fruitList[position]
            Toast.makeText(this,fruit.name,Toast.LENGTH_SHORT).show()
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
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}