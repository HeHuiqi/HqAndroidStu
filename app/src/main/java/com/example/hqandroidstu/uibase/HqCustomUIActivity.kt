package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hqandroidstu.R

class HqCustomUIActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context,HqCustomUIActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_custom_uiactivity)
        setup()
        hideActionBar()
    }

    private fun setup() {
        handleActionBarClick()
    }
    private fun handleActionBarClick() {
        val actionBar:HqCustomActionBar = findViewById(R.id.hq_custom_action_bar)
        actionBar.canFinishActivity = false
        actionBar.setOnButtonClick { buttonType ->
            when(buttonType) {
                HqCustomActionBar.HqCustomActionBarButtonType.ACTION_BAR_LEFT_BUTTON -> {
                    Toast.makeText(this@HqCustomUIActivity,"Left btn click",Toast.LENGTH_SHORT).show()
                    if (!actionBar.canFinishActivity) {
                        println("处理自定义逻辑")
                        this.finish()
                    }

                }
                HqCustomActionBar.HqCustomActionBarButtonType.ACTION_BAR_RIGHT_BUTTON -> {
                    Toast.makeText(this@HqCustomUIActivity,"Right btn click",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}