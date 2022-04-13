package com.huading.configure.net.observer

import android.net.ParseException
import com.google.gson.JsonParseException
import com.huading.configure.Constants
import com.huading.configure.base.BaseApplication
import com.huading.configure.base.BaseBean
import com.huading.configure.base.BaseViewModel
import com.huading.configure.net.http.ApiException
import com.huading.configure.utils.LogUtil
import com.huading.configure.utils.toast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import java.util.*

abstract class BaseObserver<T>(val vm: BaseViewModel) : Observer<BaseBean<T>> {

    abstract fun error(code: Int, msg: String)

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        vm.start()
    }

    override fun onError(e: Throwable) {
        try {
            when (e) {
                is ApiException -> {
                    if (e.isTokenExpried) {
                        vm.tokenExpried()
                        //TODO token失效处理

                        return
                    }
                    fail(e.erroCode, e.message)
                }
                is JSONException, is ParseException, is JsonParseException -> {
                    fail(Constants.NET_CODE_PARSE_EXCEPTION, e.message)
                }
                is UnknownServiceException, is IllegalArgumentException -> {
                    fail(Constants.NET_CODE_SERVER_EXCEPTION, Objects.requireNonNull(e.message))
                }
                is HttpException, is UnknownHostException -> {
                    fail(Constants.NET_CODE_COLLECTION_EXCEPTION, Objects.requireNonNull(e.message))
                }
                is SocketException -> {
                    fail(Constants.NET_CODE_SCOKET_EXCEPTION, Objects.requireNonNull(e.message))
                }
                else -> {
                    fail(Constants.NET_CODE_ERROR, e.message)
                }
            }
        } finally {
            onFinish()
        }
    }

    fun fail(code: Int, msg: String?) {
        vm.end()
        LogUtil.i("BaseObserver  fail ${code},$msg")
        when (code) {
            Constants.NET_CODE_FAIL -> {
            }
            Constants.NET_CODE_ERROR, Constants.NET_CODE_SERVER_EXCEPTION, Constants.NET_CODE_COLLECTION_EXCEPTION -> {
                toast("网络请求失败", BaseApplication().sInstance)
            }
            Constants.NET_CODE_PARSE_EXCEPTION -> {
                toast("数据解析异常", BaseApplication().sInstance)
            }
            else -> {
                msg?.let {
                    toast(it, BaseApplication().sInstance)
                }
            }
        }
        error(code, msg!!)
    }

    fun onFinish() {
        vm.end()
    }

}