package com.example.hqandroidstu.uibase

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqUicompotentBinding
import com.example.hqandroidstu.utils.showToast


enum class UICompetent(name: String) {
    PopupWindow("PopupWindow"),
    HqCustomTitleView("HqCustomTitleView")
}
class HqUICompetentActivity : AppCompatActivity() {
    
    
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqUICompetentActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val competentList = ArrayList<String>()
    private val rootBinding:ActivityHqUicompotentBinding by lazy {
        ActivityHqUicompotentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        initList()
        initView()
    }
    private fun initList() {
        competentList.add(UICompetent.PopupWindow.name)
        competentList.add(UICompetent.HqCustomTitleView.name)

    }
    private fun initView() {
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,competentList)
        rootBinding.listView.adapter = adapter
        rootBinding.listView.setOnItemClickListener { _, _, position, _ ->
            when(competentList[position]) {
                UICompetent.PopupWindow.name -> {
                    showPopWindow()

                }
                UICompetent.HqCustomTitleView.name -> {
                    HqCustomViewActivity.actionStart(this)
                }
            }

        }
    }

    private fun showPopWindow() {

        val popContentView: View =
            LayoutInflater.from(this).inflate(R.layout.popwindow_tip, null,false)
        val popWnd = PopupWindow(this)
        popWnd.contentView = popContentView
        popWnd.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popWnd.height = ViewGroup.LayoutParams.WRAP_CONTENT
        //不设置背景会有灰色的背景
//        popWnd.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.btn_bg,null))
        popWnd.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 设置PopupWindow是否能响应外部点击事件
        popWnd.isOutsideTouchable = true
        // pop 显示时， 是否可以点击
        popWnd.isTouchable = true
        // pop 显示时， 不让外部 view 响应点击事件
        popWnd.isFocusable = true

        //显示前让背景变暗
         bgAlpha(0.5)
        //设置显示位置
        popWnd.showAtLocation(
            rootBinding.listView,
            Gravity.CENTER,
            0,
            0)
        val contentTv:TextView = popContentView.findViewById(R.id.contentTv)

        contentTv.setOnClickListener {
            //隐藏PopupWindow
            popWnd.dismiss()
            "hello".showToast()
        }
        popWnd.setOnDismissListener {
            //隐藏后让背景恢复
            bgAlpha(1.0)
        }



        //三秒后自动消失
        var context:Context?
        rootBinding.listView.postDelayed(Runnable {
            popWnd.dismiss()
        },3000)
    }
    private fun bgAlpha(alpha: Double) {
     val  lp = window.attributes;
        lp.alpha = alpha.toFloat(); //0.0-1.0
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.attributes = lp;
    }

    private fun  screenSize():Size{
        val  windowManager:WindowManager =  getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics;
            Size(metrics.bounds.width(),metrics.bounds.height())
        } else {
            val width = windowManager.defaultDisplay?.width ?:0
            val height = windowManager.defaultDisplay?.height ?:0
            Size(width,height)
        }
    }
}