package com.example.hqandroidstu

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hqandroidstu.databinding.FirstLayoutBinding

class HqFirstActivity : HqBaseActivity() {

    private lateinit var rootBind:FirstLayoutBinding
    private lateinit var requestDataLauncher:ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


         //setContentView()方法来给当前的Activity加载一个布局
        //通过视图ID设置根视图
//        setContentView(R.layout.first_layout)

        //初始化视图绑定对象
        rootBind = FirstLayoutBinding.inflate(layoutInflater)
        //设置根视图
        setContentView(rootBind.root)

        //注册启动器 requestDataLauncher:ActivityResultLauncher<Intent>
        requestDataLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult? ->
            Log.d("HHQ", ": ${result?.data?.getStringExtra("hq_return_data_key")}")
            //获取回传的数据并更新视图
            rootBind.tv1.text = result?.data?.getStringExtra("hq_return_data_key")
        }

        setup()
    }

    private fun setup(){
        //通过ID获取子视图
//        val btn:Button = findViewById(R.id.button1)
//        btn.setOnClickListener {
//            Toast.makeText(this,"我是一个Toast",Toast.LENGTH_SHORT).show()
//
//        }

        //开启视图绑定后，可以通过视图绑定对象直接获取子视图
        rootBind.button1.setOnClickListener {
            Toast.makeText(this,"我是一个Toast",Toast.LENGTH_SHORT).show()
//            pushActivity()
//            openUrl()
//            callPhone()
//            openActivityPassData()
//            openActivityPassDataForResult()
            openActivityPassDataUseFun()
        }
    }
    private fun openActivityPassDataUseFun(){
        HqSecondActivity.actionStart(this,"Hello1","Hello2")
    }
    private fun openActivityPassDataForResult(){
        val intent = Intent(this,HqSecondActivity::class.java)
        //传递数据
        val data = "Hello Android"
        val key = "hq_data_key"
        intent.putExtra(key,data)

        //方法被废弃，通过 重写 onActivityResult() 的函数来接收回传数据
//        startActivityForResult(intent,1)

        //替代方法，通过启动器启动intent,通过 requestDataLauncher 的回调来接收回传数据
        requestDataLauncher.launch(intent)
    }
    private fun openActivityPassData(){
        val intent = Intent(this,HqSecondActivity::class.java)
        //传递数据
        val data = "Hello Android"
        val key = "hq_data_key"
        intent.putExtra(key,data)
        startActivity(intent)
    }
    private fun openUrl(){
        //这个将会使用系统默认的浏览器打开url
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://www.baidu.com")
        startActivity(intent)
    }
    private fun  callPhone() {
        //拨号
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:10086")
        startActivity(intent)
    }
    private fun pushActivity() {
        //pop activity
//            finish()
            val intent = Intent(this,HqSecondActivity::class.java)
            startActivity(intent)

        //隐式启动
        // 这里的action要和 AndroidManifest.xml中Activity的配置名称保持一致，才能正确启动
//        val intent = Intent("com.example.hqandroidstu.HQ_ACTION_START")
//        //添加自定义的Category，这里自定义的Category必须在 AndroidManifest.xml 先配置，
//        // 否则会崩溃或者回到主Activity
//        intent.addCategory("com.example.hqandroidstu.category.HQ_SENCOND")
//        startActivity(intent)
    }
    //为页面添加菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //将自定义的菜单加载，加载后会在导航条右侧显示三个点，点击即可显示菜单选项
        menuInflater.inflate(R.menu.first_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    // 处理菜单选中事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> {
                Toast.makeText(this,"Clicked Add",Toast.LENGTH_SHORT).show()
            }
            R.id.remove_item -> {
                Toast.makeText(this,"Clicked Remove",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult", "onActivityResult: ")
        when (requestCode) {
            1 -> {
                if (resultCode == RESULT_OK) {
                    rootBind.tv1.text = data?.getStringExtra("hq_return_data_key")
                }
            }
        }
    }

}