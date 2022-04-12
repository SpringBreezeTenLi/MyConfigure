package com.huading.configure.base

/**
 * 数据模型公共基类
 */
data class BaseBean<T>(val code: Int, val data: T?, var msg: String) {
    companion object {
        const val SUCCESS = 200
        const val ERROR = 1
        const val EXCEPTION = 2
        const val LOADING = 3

        fun <T> success(data: T?) = BaseBean(SUCCESS, data, "success")

        fun <T> error(msg: String, data: T?) = BaseBean(ERROR, data, msg)

        fun <T> exception(msg: String, data: T?) = BaseBean(ERROR, data, msg)

        fun <T> loading(data: T?) = BaseBean(LOADING, data, "loading")
    }
}