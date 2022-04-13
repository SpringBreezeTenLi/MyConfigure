package com.huading.configure.net.http

import com.huading.configure.Constants
import com.huading.configure.base.BaseApplication
import com.huading.configure.utils.toast

class DisposableErrorTools {

    fun errorBack(e: Throwable) {
        val code = ExceptionManager.getInstance().Throwable(e).code
        val msg = ExceptionManager.getInstance().Throwable(e).message
        when (code) {
            Constants.NET_CODE_TOKEN_EXPIRED -> {
                //TODO token失效处理

            }
            else -> {
                msg?.let {
                    toast(it, BaseApplication().sInstance)
                }
            }
        }
    }
}