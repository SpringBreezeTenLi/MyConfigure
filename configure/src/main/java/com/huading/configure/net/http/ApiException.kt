package com.huading.configure.net.http

import com.huading.configure.Constants

class ApiException(private val mErrorCode: Int, errorMessage: String?) :
    RuntimeException(errorMessage) {

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    val isTokenExpried: Boolean
        get() = mErrorCode == Constants.NET_CODE_TOKEN_EXPIRED

    val erroCode = mErrorCode

}