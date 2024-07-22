package com.example.hqandroidstu.first

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.hqandroidstu.HqBaseActivity
import com.example.hqandroidstu.databinding.ActivityHqSecondBinding

class HqSecondActivity : HqBaseActivity() {

    // Kotlin规定，所有定义在companion object中的方法都可以使用类似于Java静态方法的形式调用
    // 类中所有静态方法和变量都可以写在这里，然后直接通过 类名.方法（变量）类调用
    // companion object这个关键字实际上会 在HqSecondActivity类的内部创建一个伴生类，
    // 而actionStart()方法就是定义在这个伴生类里面的实例方法
    // 始终只会存在一个伴生类对象
    companion object {
        fun actionStart(context: Context,data1:String,data2:String) {
//            val intent = Intent(context,HqSecondActivity::class.java)
//            intent.putExtra("hq_data_key",data1)
//            intent.putExtra("hq_data_key2",data2)
//            context.startActivity(intent)

            //通过apply函数来简化参数传递
            val intent = Intent(context, HqSecondActivity::class.java).apply {
                putExtra("hq_data_key", data1)
                putExtra("hq_data_key2", data2)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityHqSecondBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHqSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //        setContentView(R.layout.activity_hq_second)

        setup()
    }
    private fun setup() {
        binding.button2.setOnClickListener {

            //回传数据
            val intent = Intent()
            intent.putExtra("hq_return_data_key","回传数据： Hello Stu")
            setResult(RESULT_OK,intent)
            finish()
        }
        //获取传递过来的数据
        val key = "hq_data_key"
        binding.tv2.text = intent.getStringExtra(key)

        binding.button21.setOnClickListener {
            val intent = Intent(this, HqThirdActivity::class.java)
            startActivity(intent)

        }
    }
}