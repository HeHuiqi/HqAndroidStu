package com.example.hqandroidstu.material

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqMaterialBinding

class HqMaterialActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqMaterialActivity::class.java)
               context.startActivity(intent)
        }
    }

    private val rootBinding:ActivityHqMaterialBinding by lazy {
        ActivityHqMaterialBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        initActionBar()

    }
    private fun initActionBar() {
        val toolbar = rootBinding.toolbar
        setSupportActionBar(toolbar)
        toolbar.title = "自定义"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.backup -> {
                Toast.makeText(this,"点击备份",Toast.LENGTH_SHORT).show()
            }
            R.id.delete -> {
                Toast.makeText(this,"点击删除",Toast.LENGTH_SHORT).show()
            }
            R.id.settings -> {
                Toast.makeText(this,"点击设置",Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}