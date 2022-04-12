package com.huading.configure.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar

class ScreenUtil {

    /**
     * 设置线性布局的view高度
     */
    fun setWedigetHeightParmas(context: Fragment, view: View) {
        var statusBarHeight = ImmersionBar.getStatusBarHeight(context)
        val linearParams: LinearLayout.LayoutParams =
            view.getLayoutParams() as LinearLayout.LayoutParams
        linearParams.height = statusBarHeight
        linearParams.width = -1
        view.setLayoutParams(linearParams)
    }

    /**
     * 设置线性布局的view高度
     */
    fun setWedigetHeightParmas(context: Activity, view: View) {
        var statusBarHeight = ImmersionBar.getStatusBarHeight(context)
        val linearParams: LinearLayout.LayoutParams =
            view.getLayoutParams() as LinearLayout.LayoutParams
        linearParams.height = statusBarHeight
        linearParams.width = -1
        view.setLayoutParams(linearParams)
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * 屏幕透明度0.0-1.0 1表示完全不透明
     */
    fun setBackgroundAlpha(bgAlpha: Float, ctx: Context) {
        val lp = (ctx as Activity).window
            .attributes
        lp.alpha = bgAlpha
        ctx.window.attributes = lp
    }

    /**
     * 获取屏幕宽度
     */
    fun getWindowWidth(ctx: Context): Int {
        val dm = DisplayMetrics()
        (ctx as Activity).windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getWindowHight(ctx: Context): Int {
        val dm = DisplayMetrics()
        (ctx as Activity).windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, value: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (value / scale + 0.5f).toInt()
    }

    /**
     * 设置状态栏透明
     */
    @TargetApi(19)
    fun setTranslucentStatus(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = activity.window
            val attributes = window.attributes
            val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            attributes.flags = attributes.flags or flagTranslucentStatus
            window.attributes = attributes
        }
    }

    /**
     * 禁用显示大小改变和文字大小
     */
    fun disabledDisplayDpiChange(res: Resources): Resources? {
        val newConfig = res.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //字体非默认值
            if (res.configuration.fontScale != 1f) {
                newConfig.fontScale = 1f
            }
            res.updateConfiguration(newConfig, res.displayMetrics)
        } else {
            //字体非默认值
            if (res.configuration.fontScale != 1f) {
                newConfig.fontScale = 1f //设置默认
                res.updateConfiguration(newConfig, res.displayMetrics)
            }
        }
        return res
    }

    /**
     * 获取手机出厂时默认的densityDpi
     */
    fun getDefaultDisplayDensity(): Int {
        return try {
            val aClass = Class.forName("android.view.WindowManagerGlobal")
            val method = aClass.getMethod("getWindowManagerService")
            method.isAccessible = true
            val iwm = method.invoke(aClass)
            val getInitialDisplayDensity = iwm.javaClass.getMethod(
                "getInitialDisplayDensity",
                Int::class.javaPrimitiveType
            )
            getInitialDisplayDensity.isAccessible = true
            val densityDpi = getInitialDisplayDensity.invoke(iwm, Display.DEFAULT_DISPLAY)
            densityDpi as Int
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

}