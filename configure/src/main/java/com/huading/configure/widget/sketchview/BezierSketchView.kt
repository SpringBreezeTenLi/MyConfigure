package com.huading.configure.widget.sketchview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.math.abs

/**
 * 画图画布
 */
class BezierSketchView  @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    BaseSketchView(context, attrs, defStyleAttr) {

    private var mLastEventX = 0f
    private var mLastEventY = 0f

    override fun onFingerDown(event: MotionEvent): Boolean {
        // 缓存本次点位
        mLastEventX = event.x
        mLastEventY = event.y
        // 每次按下的时候，将path移动到此点，否则会出现多余的直线
        return super.onFingerDown(event)
    }

    // 手指在屏幕上滑动时调用
    override fun onFingerMove(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        val previousX = mLastEventX
        val previousY = mLastEventY

        // 计算本次滑动距离
        val dx = abs(eventX - previousX)
        val dy = abs(eventY - previousY)

        // 两点之间的距离大于等于3时，生成贝塞尔绘制曲线
        if (dx >= 3 || dy >= 3) {
            // 设置贝塞尔曲线的操作点为起点和终点的一半
            val controlX = (eventX + previousX) / 2
            val controlY = (eventY + previousY) / 2

            // 二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，controlX, controlY为终点
            mPath.quadTo(previousX, previousY, controlX, controlY)

            // 第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mLastEventX = eventX
            mLastEventY = eventY
        }
        return true
    }
}