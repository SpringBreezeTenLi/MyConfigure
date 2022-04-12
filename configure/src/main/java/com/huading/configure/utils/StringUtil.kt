package com.huading.configure.utils

import android.text.TextUtils
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class StringUtil {

    /**
     * 非空判断
     * return string
     */
    fun isNull(key: String?): String {
        return if (TextUtils.isEmpty(key)) "" else "${if (key == "null") "" else key}"
    }

    /**
     * 非空判断
     * return int
     */
    fun isNullBackZero(key: String?): String {
        return if (TextUtils.isEmpty(key)) "0" else "${if (key == "null") "0" else subZero(key)}"
    }

    /**
     * 去除无用的0
     */
    fun subZero(key: String?): String {
        val keys = BigDecimal(key)
        val keyValue = keys.stripTrailingZeros()
        return keyValue.toPlainString()
    }

    var dfs: DecimalFormat? = null

    /**
     * 格式化
     */
    fun format(pattern: String): DecimalFormat? {
        if (dfs == null) {
            dfs = DecimalFormat()
        }
        dfs?.setRoundingMode(RoundingMode.FLOOR)
        dfs?.applyPattern(pattern)
        return dfs
    }

    /**
     * 数字过滤
     */
    fun numberFilter(number: Int): String {
        return when {
            number in 10000..999999 -> {  //数字上万，小于百万，保留一位小数点
                val df2 = DecimalFormat("##.#")
                val format = df2.format(number.toFloat() / 10000.toDouble())
                format + "万"
            }
            number in 1000000..99999998 -> {  //百万到千万不保留小数点
                (number / 10000).toString() + "万"
            }
            number > 99999999 -> { //上亿
                val df2 = DecimalFormat("##.#")
                val format = df2.format(number.toFloat() / 100000000.toDouble())
                format + "亿"
            }
            else -> {
                number.toString() + ""
            }
        }
    }
}