package com.example.hqandroidstu.kotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqDataPassShowBinding

class HqDataPassShowActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context,product: HqProduct) {
               val intent = Intent(context, HqDataPassShowActivity::class.java)
            intent.putExtra("product",product)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqDataPassShowBinding by lazy {
        ActivityHqDataPassShowBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        val  product = intent.getSerializableExtra("product") as HqProduct
        rootBinding.hqDataShowTv.text = buildString {
            append(product.name)
            append(product.address)
            append(product.price)
        }

    }
}