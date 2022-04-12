package com.huading.configure.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.huading.configure.R
import com.huading.configure.utils.ScreenUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mActivity: BaseActivity
    lateinit var mContext: Context

    val mScope: CoroutineScope by lazy {
        MainScope()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        ScreenUtil().disabledDisplayDpiChange(this.resources)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mActivity = this
        mContext = this
        setContentView(getLayoutId())
        ScreenUtil().setTranslucentStatus(this)

        init()
        initView()
        initData()
        initLisenter()
        initViewModel()
        initBundle(savedInstanceState)
    }

    abstract fun getLayoutId(): Int

    abstract fun init()

    open fun initBundle(savedInstanceState: Bundle?) {

    }

    open fun initViewModel() {

    }

    open fun initData() {

    }

    open fun initView() {

    }

    open fun initLisenter() {

    }

    /**
     * 设置 app 字体不随系统字体设置改变
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        if (res != null) {
            val config = res.configuration
            if (config != null) {
                config.fontScale = 1.0f
                res.updateConfiguration(config, res.displayMetrics)
            }
        }
        return res
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    open fun initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).statusBarDarkFont(true, 0.2f)
            .navigationBarColor(R.color.white).init()
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    open fun initImmersionBar(isDarkFont: Boolean) {
        ImmersionBar.with(this).keyboardEnable(true).statusBarDarkFont(isDarkFont)
            .navigationBarColor(R.color.white).init()
    }

    /**
     * scrollLayout  false   软键盘弹出  布局不上移
     */
    open fun initImmersionBar(isDarkFont: Boolean, scrollLayout: Boolean) {
        ImmersionBar.with(this).keyboardEnable(scrollLayout).statusBarDarkFont(isDarkFont)
            .navigationBarColor(R.color.white).init()
    }

    override fun onDestroy() {
        super.onDestroy()
        mScope.cancel()
    }
}