package com.example.hqandroidstu.uibase

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.example.hqandroidstu.R

class HqCustomActionBar(context: Context, attrs:AttributeSet): LinearLayout(context,attrs) {

    enum class HqCustomActionBarButtonType {
        ACTION_BAR_LEFT_BUTTON,
        ACTION_BAR_RIGHT_BUTTON,
    }
    interface HqCustomActionBarAction {
        fun onLeftButtonClick(){}
        fun onRightButtonClick(){}
    }
    var canFinishActivity:Boolean = true

    private  var buttonClickAction: ((HqCustomActionBarButtonType) -> Unit?)? = null

    private var customActionBarAction :HqCustomActionBarAction? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.actionbar_custom,this)

        //统一处理按钮事件
        val backBtn: Button = findViewById(R.id.action_bar_left_btn)
        backBtn.setOnClickListener {
            buttonClickAction?.let {
                it1 -> it1(HqCustomActionBarButtonType.ACTION_BAR_LEFT_BUTTON)
            }
            if (buttonClickAction == null) {
                customActionBarAction?.onLeftButtonClick()
            }

            if (canFinishActivity) {
                //as 强制类型转换
                val activity = context as Activity
                activity.finish()
            }
        }

        val rightBtn: Button = findViewById(R.id.action_bar_right_btn)
        rightBtn.setOnClickListener {
            buttonClickAction?.let { it1 -> it1(HqCustomActionBarButtonType.ACTION_BAR_RIGHT_BUTTON) }
            if (buttonClickAction == null) {
                customActionBarAction?.onRightButtonClick()
            }
        }

    }
    /*
      处理回调方式1，推荐
      使用方式：
        actionBar.setOnButtonClick { buttonType ->
            when(buttonType) {
                HqCustomActionBar.HqCustomActionBarButtonType.ACTION_BAR_LEFT_BUTTON -> {
                    Toast.makeText(this@HqCustomUIActivity,"Left btn click",Toast.LENGTH_SHORT).show()

                }
                HqCustomActionBar.HqCustomActionBarButtonType.ACTION_BAR_RIGHT_BUTTON -> {
                    Toast.makeText(this@HqCustomUIActivity,"Right btn click",Toast.LENGTH_SHORT).show()
                }
            }
        }
    * */
    fun setOnButtonClick(callback: ((btnId: HqCustomActionBarButtonType) -> Unit)?) {
        buttonClickAction = callback

    }
    /*
    处理回调方式2
    使用方式：
     actionBar.setOnButtonClick(actionBarAction = object : HqCustomActionBar.HqCustomActionBarAction {
            override fun onLeftButtonClick() {

            }
            override fun onRightButtonClick() {
            }
        })
     */
    fun setOnButtonClick2(actionBarAction: HqCustomActionBarAction?) {
        customActionBarAction = actionBarAction
    }

}