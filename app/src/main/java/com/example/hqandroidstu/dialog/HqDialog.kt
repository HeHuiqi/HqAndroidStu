package com.example.hqandroidstu.dialog

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.HqCustomDialogBinding
import com.example.hqandroidstu.utils.DensityUtil


class HqDialog(val hqContext: Context):AppCompatDialog(hqContext){
    private lateinit var cancelBtn:Button
    private lateinit var confirmBtn:Button
    private lateinit var titleTv:TextView
    private lateinit var messageTv:TextView
    private lateinit var checkBox: CheckBox

    var onCancelClick: (() -> Unit?)? = null
    var onConfirmClick: (() -> Unit?)? = null
    var onCheckBoxClick: ((Boolean) -> Unit?)? = null


    private val rootBinding:HqCustomDialogBinding by lazy {
        HqCustomDialogBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        window?.setBackgroundDrawableResource(R.drawable.hq_corner_bg)
        setup()

    }

    private fun setup() {
        //点击背景不让其消失
        setCanceledOnTouchOutside(false)
//        titleTv = findViewById(R.id.dialogTitleTv)!!
//        messageTv = findViewById(R.id.dialogMessageTv)!!
//        checkBox = findViewById(R.id.dialogCheckBox)!!
//        cancelBtn = findViewById(R.id.dialogCancelBtn)!!
//        confirmBtn = findViewById(R.id.dialogConfirmBtn)!!

        titleTv = rootBinding.dialogTitleTv
        messageTv = rootBinding.dialogMessageTv
        checkBox = rootBinding.dialogCheckBox
        cancelBtn = rootBinding.dialogCancelBtn
        confirmBtn = rootBinding.dialogConfirmBtn


//        val drawable = ContextCompat.getDrawable(hqContext,R.drawable.hq_custom_checkbox)
//        val desiredSize = TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            5f,
//            hqContext.resources.displayMetrics
//        ).toInt()
//        val newDrawable = DrawableCompat.wrap(
//            drawable!!
//        ).mutate()
//        DrawableCompat.setHotspotBounds(newDrawable, 0, 0, desiredSize, desiredSize)
//        checkBox.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null);



        checkBox.setOnClickListener {
            onCheckBoxClick?.let {
                it(checkBox.isChecked)
            }
        }
        cancelBtn.setOnClickListener {
            onCancelClick?.let {
                it()
            }
            dismiss()
        }
        confirmBtn.setOnClickListener {
            onConfirmClick?.let {
                it()
            }
            dismiss()
        }

        Looper.myQueue().addIdleHandler {
            //重新设置drawableStart的大小
            val drawable = checkBox.compoundDrawables[0]
            drawable.bounds = Rect(0,0,DensityUtil.dp2px(14f),DensityUtil.dp2px(14f))
            checkBox.setCompoundDrawables(drawable,null,null,null)

            false
        }


    }
    fun setDialogInfo(title:String,msg:String,hideCheck:Boolean = false) {
        //保证在UI完全展示后设置
        Looper.myQueue().addIdleHandler {
            titleTv.text = title
            messageTv.text = msg
            if (hideCheck) {
                checkBox.visibility = View.GONE
            } else {
                checkBox.visibility = View.VISIBLE
            }
            false
        }

    }

}