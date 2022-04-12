package com.huading.configure.widget.sketchview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

abstract class BaseSketchView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    // Paint 一只画笔
    private val mPaint = Paint()

    // Path 用于记录路径
    val mPath = Path()

    /**
     * 画笔颜色，初始化时使用，默认：Color.RED
     * 子类重写此方法修改颜色
     */
    open fun penColor() = Color.RED

    /**
     * 画笔宽度，初始化时使用，默认：10f
     * 子类重写此方法修改宽度
     */
    open fun strokeWidth() = 10f

    /**
     * 重写触摸事件监听并消费掉，不让其往下传递
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 事件处理
        val needInvalidate = when (event.action) {
            // 1.手指按下
            MotionEvent.ACTION_DOWN -> onFingerDown(event)
            // 2.手指滑动
            MotionEvent.ACTION_MOVE -> onFingerMove(event)
            // 3.手指抬起
            MotionEvent.ACTION_UP -> onFingerUp(event)
            // 默认不重绘
            else -> false
        }
        if (needInvalidate) {
            // 重绘
            invalidate()
        }
        return true
    }

    /**
     * 手指按下
     */
    open fun onFingerDown(event: MotionEvent): Boolean {
        // 每次按下的时候，将path移动到此点，否则会出现多余的直线
        mPath.moveTo(event.x, event.y)
        return true
    }

    /**
     * 手指移动，必须在子类中实现，这里就是画笔迹的核心
     */
    abstract fun onFingerMove(event: MotionEvent): Boolean

    /**
     * 手指抬起
     */
    open fun onFingerUp(event: MotionEvent): Boolean {
        return false
    }

    /**
     * 清空画布
     */
    fun clear() {
        // 清空path
        mPath.reset()
        // 重绘
        invalidate()
    }

    /**
     * 绘制
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 通过画布绘制多点形成的图形
        canvas?.drawPath(mPath, mPaint)
    }

    /**
     * 初始化画笔，默认颜色：红色 宽度(px)：10f
     */
    private fun initPaint(color: Int = Color.RED, strokeWidth: Float = 10f) {
        // 设置画笔颜色
        mPaint.color = color
        // 设置画笔宽度(单位px)
        mPaint.strokeWidth = strokeWidth
        // 设置画笔样式
        mPaint.style = Paint.Style.STROKE
        // 画笔开始和结束为圆
        mPaint.strokeCap = Paint.Cap.ROUND
        // 连接处为圆
        mPaint.strokeJoin = Paint.Join.ROUND
        // 当style为STROKE或FILL_AND_STROKE时设置连接处的倾斜度，这个值必须大于0
        mPaint.strokeMiter = 1.0f
        // 设置画笔透明度
        mPaint.alpha = 0xFF
        // 设置抗锯齿
        mPaint.isAntiAlias = true
    }

    init {
        initPaint(color = penColor(), strokeWidth = strokeWidth())
    }
}