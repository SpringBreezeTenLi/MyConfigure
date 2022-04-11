package com.huading.configure.utils

import android.text.TextUtils
import java.math.BigDecimal

class StringUtil {

    fun isNull(key: String?): String {
        return if (TextUtils.isEmpty(key)) "" else "${if (key == "null") "" else key}"
    }

    fun isNullBackZero(key: String?): String {
        return if (TextUtils.isEmpty(key)) "0" else "${if (key == "null") "0" else subZero(key)}"
    }

    fun subZero(key: String?): String {
        val keys = BigDecimal(key)
        val keyValue = keys.stripTrailingZeros()
        return keyValue.toPlainString()
    }

}