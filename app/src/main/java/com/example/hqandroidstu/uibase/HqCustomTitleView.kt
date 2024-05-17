package com.example.hqandroidstu.uibase

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.hqandroidstu.R


/**
 * TODO: document your custom view class.
 */
class HqCustomTitleView : View {

    private var _titleText: String? = null // TODO: use a default from R.string...
    private var _titleTextColor: Int = Color.RED // TODO: use a default from R.color...
    private var _titleTextSize: Float = 0f // TODO: use a default from R.dimen...

    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f
    private var mBound: Rect? = null
    private var mPaint: Paint? = null

    /**
     * The text to draw
     */
    var titleText: String?
        get() = _titleText
        set(value) {
            _titleText = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var titleTextColor: Int
        get() = _titleTextColor
        set(value) {
            _titleTextColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var titleTextSize: Float
        get() = _titleTextSize
        set(value) {
            _titleTextSize = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var titleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.HqCustomTitleView, defStyle, 0
        )

        _titleText = a.getString(
            R.styleable.HqCustomTitleView_titleText
        )
        _titleTextColor = a.getColor(
            R.styleable.HqCustomTitleView_titleTextColor,
            _titleTextColor
        )
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _titleTextSize = a.getDimension(
            R.styleable.HqCustomTitleView_titleTextSize,
            _titleTextSize
        )

        if (a.hasValue(R.styleable.HqCustomTitleView_titleDrawable)) {
            titleDrawable = a.getDrawable(
                R.styleable.HqCustomTitleView_titleDrawable
            )
            titleDrawable?.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()

        /**
         * 获得绘制文本的宽和高
         */
        mPaint =  Paint();
        mPaint!!.textSize = titleTextSize
        mBound = Rect();
        val length = titleText?.length ?:0
        mPaint!!.getTextBounds(titleText, 0, length, mBound)
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = titleTextSize
            it.color = titleTextColor
            textWidth = it.measureText(titleText)
            textHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        titleText?.let {
            // Draw the text.
            canvas.drawText(
                it,
                paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + (contentHeight + textHeight) / 2,
                textPaint
            )
        }

        // Draw the example drawable on top of the text.
        titleDrawable?.let {
            it.setBounds(
                paddingLeft, paddingTop,
                paddingLeft + contentWidth, paddingTop + contentHeight
            )
            it.draw(canvas)
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            mPaint!!.textSize = titleTextSize
            val length = titleText?.length ?:0
            mPaint!!.getTextBounds(titleText, 0, length, mBound)
            val width1 = mBound?.width()?.toFloat() ?: 0
            val textWidth: Float = width1.toFloat()
            val desired = (paddingLeft + textWidth + paddingRight).toInt()
            desired
        }
        val height: Int = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            mPaint!!.textSize = titleTextSize
            val length = titleText?.length ?:0
            mPaint!!.getTextBounds(titleText, 0, length, mBound)
            val height1 = mBound?.height()?.toFloat() ?: 0
            val textHeight: Float = height1.toFloat()
            val desired = (paddingTop + textHeight + paddingBottom).toInt()
            desired
        }
        setMeasuredDimension(width, height)
    }
}