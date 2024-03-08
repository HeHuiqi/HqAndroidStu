package com.example.hqandroidstu.material

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqFruitBinding
import com.example.hqandroidstu.uibase.HqFruit

class HqFruitActivity : AppCompatActivity() {



    companion object{

        const val FRUIT_IMAGE = "fruit_name"
        const val FRUIT_IMAGE_ID = "fruit_image_id"
        fun actionStart(context: Context,fruit:HqFruit) {
            val intent = Intent(context, HqFruitActivity::class.java).apply {
                putExtra(FRUIT_IMAGE,fruit.name)
                putExtra(FRUIT_IMAGE_ID,fruit.imgId)
            }
            context.startActivity(intent)
        }
    }

    private val rootBinding:ActivityHqFruitBinding by lazy {
        ActivityHqFruitBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }

    private fun setup() {

        val name = intent.getStringExtra(FRUIT_IMAGE) ?: ""
        val imageId = intent.getIntExtra(FRUIT_IMAGE_ID,0)
        setupActionBar(name)

        Glide.with(this).load(imageId).into(rootBinding.hqFruitBigImageView)
        rootBinding.hqFruitContentTv.text = createFruitContent(name)
    }
    private fun setupActionBar(title:String) {
        setSupportActionBar(rootBinding.hqFruitToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rootBinding.collapsingToolbarLayout.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createFruitContent(name:String) = name.repeat(500)


}