package com.example.hqandroidstu.jetpack

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqJetpackStuBinding

//     implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
class HqJetpackStuActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqJetpackStuActivity::class.java)
               context.startActivity(intent)
        }
    }

    private val rootBinding:ActivityHqJetpackStuBinding by lazy {
        ActivityHqJetpackStuBinding.inflate(layoutInflater)
    }
    private lateinit var sp:SharedPreferences
    private  val viewModel: HqJetpackStuViewModel2 by lazy {
        // 绝对不可以直接去创建ViewModel的实 例，而是一定要通过ViewModelProvider来获取ViewModel的实例
        // 因为ViewModel有其独立的生命周期，并且其生命周期要长于Activity
        // 通过viewMode来持有的数据，Activity重建时其数据不对重置
        // 通过HqJetpackStuViewModelFactory来对构造函数初始化值
        sp = getPreferences(Context.MODE_PRIVATE)
        val saveCount = sp.getInt("save_count",0)
        ViewModelProvider(this,HqJetpackStuViewModelFactory(saveCount)).get(HqJetpackStuViewModel2::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    Log.d("HqJetpackStuActivity1", "onCreate: ")
    }

    private fun setup() {

        // 只 要你的Activity是继承自AppCompatActivity的，或者你的Fragment是继承自 androidx.fragment.app.Fragment的，那么它们本身就是一个LifecycleOwner的实例
        //添加Activity声明周期观察者
        lifecycle.addObserver(HqLifeCycleObserver(lifecycle))

        //给model的LiveData属性counter添加观察者，其值变化时会自动回调

        //方式1
//        viewModel.counter.observe(this, Observer { count ->
//            rootBinding.countInfoTv.text = count.toString()
//
//        })

        //方式2
        viewModel.counter.observe(this){ count ->
            rootBinding.countInfoTv.text = count.toString()

        }

        //UI事件

        rootBinding.addBtn.setOnClickListener {
            viewModel.plusOne()
        }
        rootBinding.clearBtn.setOnClickListener {
            viewModel.clear()
        }
    }
    override fun onPause() {
        super.onPause()
        sp.edit{
            putInt("save_count",viewModel.currentCount())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sp.edit{
            putInt("save_count",viewModel.currentCount())
        }
    }
    
    
}