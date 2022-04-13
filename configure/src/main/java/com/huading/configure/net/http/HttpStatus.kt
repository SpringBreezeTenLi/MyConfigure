package com.huading.configure.net.http

import com.google.gson.annotations.SerializedName
import com.huading.configure.Constants

/**
 * 网络请求成功失败code
 */
class HttpStatus {
    @SerializedName("code")
    val code = Constants.NET_CODE_SUCCESS

    @SerializedName("msg")
    val message: String? = null

    /**
     * API是否请求失败
     * @return 失败返回true, 成功返回false
     */
    val isCodeInvalid: Boolean
        get() = code != Constants.NET_CODE_SUCCESS
}