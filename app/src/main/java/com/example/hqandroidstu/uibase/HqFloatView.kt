package com.example.hqandroidstu.uibase

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import com.example.hqandroidstu.R
import kotlin.math.abs

class HqFloatView:LinearLayout {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
            super(context, attributeSet, defStyleAttr) {
    }

    private var displayMetrics: DisplayMetrics = resources.displayMetrics
    //宽度
    private var screenWidth = displayMetrics.widthPixels
    //高度
    private var screenHeight = displayMetrics.heightPixels

    private var lastX: Float = 0.0f
    private var lastY: Float = 0.0f
    private val leftRightSpaceLimit:Int = 60
    private val topSpaceLimit:Int = 200
    private val bottomSpaceLimit:Int = 80
    private var  isMoving = false
    private var downTime = 0L



    private  var buttonClickAction: ((Button) -> Unit?)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.hq_float_view,this)

        //统一处理按钮事件
        val backBtn: Button = findViewById(R.id.hq_float_btn)
        backBtn.setOnClickListener {
            Log.i("hq-float-isMoving", "$isMoving")

            if (!isMoving) {
                buttonClickAction?.let { it1 -> it1(backBtn) }
            }
        }

    }
    fun setOnButtonClick(callback: ((view: Button) -> Unit)?) {
        buttonClickAction = callback
    }
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.action ?: return super.onInterceptTouchEvent(ev)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.x
                lastY = ev.y
                downTime = System.currentTimeMillis()
                isMoving = false
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - lastX
                val dy = ev.y - lastY

                //如果移动距离太小就不更新
                if (abs(dx) < 10 && abs(dy) < 10) {
                    return super.onInterceptTouchEvent(ev)
                }

                var l = left + dx.toInt()
                var r = right + dx.toInt()
                var t = top + dy.toInt()
                var b = bottom + dy.toInt()
                //当滑动出边界时需要重新设置位置
                if (l <= leftRightSpaceLimit) {
                    l = leftRightSpaceLimit
                    r = width + leftRightSpaceLimit
                }
                if (r >= screenWidth - leftRightSpaceLimit ) {
                    r = screenWidth - leftRightSpaceLimit
                    l = screenWidth - width - leftRightSpaceLimit
                }

                if (t <= topSpaceLimit) {
                    t = topSpaceLimit
                    b = height + topSpaceLimit
                }

                if (b >= screenHeight - bottomSpaceLimit) {
                    b = screenHeight - bottomSpaceLimit
                    t = screenHeight - height - bottomSpaceLimit
                }

                Log.i("hq-float-view", "$l,$r,$t,$b")

                layout(l, t, r, b)
                Log.i("hq-float-view1", "$left,$right,$top,$bottom")

                isMoving = true

            }
            MotionEvent.ACTION_UP -> {
                //点击按下抬起小于100毫秒，就执行点击事件
                isMoving = if (System.currentTimeMillis() - downTime < 100) {
                    performClick()
                    false
                } else {
                    true
                }
                if (left < screenWidth/2) {
                    val l = leftRightSpaceLimit
                    val r = width + leftRightSpaceLimit
                    layout(l,top,r,bottom)
                }
                if (right > screenWidth/2) {
                    val r = screenWidth - leftRightSpaceLimit
                    val l = screenWidth - width - leftRightSpaceLimit
                    layout(l,top,r,bottom)
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

}