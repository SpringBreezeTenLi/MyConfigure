package com.huading.configure.net.observer

import com.huading.configure.Constants
import com.huading.configure.base.BaseBean
import com.huading.configure.base.BaseViewModel

abstract class NetObserver<T>(vm: BaseViewModel) : BaseObserver<T>(vm) {

    abstract fun success(data: T, code: Int, msg: String)

    override fun onNext(t: BaseBean<T>) {
        try {
            if (Constants.NET_CODE_SUCCESS == t.code) {
                success(t.data!!, t.code, t.msg)
            } else {
                fail(t.code, t.msg)
            }
        } finally {
            onFinish()
        }
    }
}