package com.huading.configure.utils

import java.math.BigDecimal
import java.text.DecimalFormat

class MoneyUtil {

    private val fnum = DecimalFormat("##0.00")

    /**
     * 格式化金额
     */
    fun formatMoney(value: String?): String? {
        var value = value
        if (value.isNullOrEmpty()) {
            value = "0.00"
        }
        return fnum.format(BigDecimal(value))
    }

    /**
     * 金额相加
     * @param valueStr 基础值
     * @param addStr 被加数
     */
    fun moneyAdd(value: BigDecimal, augend: BigDecimal?): BigDecimal? {
        return value.add(augend)
    }

    fun moneyAdd(valueStr: String?, addStr: String?): String? {
        val value = BigDecimal(valueStr)
        val augend = BigDecimal(addStr)
        return fnum.format(value.add(augend))
    }

    /**
     * 金额相减
     * @param value 基础值
     * @param subtrahend 减数
     */
    fun moneySub(value: BigDecimal, subtrahend: BigDecimal?): BigDecimal? {
        return value.subtract(subtrahend)
    }

    fun moneySub(valueStr: String?, minusStr: String?): String? {
        val value = BigDecimal(valueStr)
        val subtrahend = BigDecimal(minusStr)
        return fnum.format(value.subtract(subtrahend))
    }

    /**
     * 金额相乘
     * @param valueStr 基础值
     * @param mulStr 被乘数
     */
    fun moneyMul(value: BigDecimal, mulValue: BigDecimal?): BigDecimal? {
        return value.multiply(mulValue)
    }

    fun moneyMul(valueStr: String?, mulStr: String?): String? {
        val value = BigDecimal(valueStr)
        val mulValue = BigDecimal(mulStr)
        return fnum.format(value.multiply(mulValue))
    }

    /**
     * 金额相除
     * 精确小位小数
     * @param valueStr 基础值
     * @param divideStr 被乘数
     */
    fun moneyDiv(value: BigDecimal, divideValue: BigDecimal?): BigDecimal? {
        return value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP)
    }

    fun moneyDiv(valueStr: String?, divideStr: String?): String? {
        val value = BigDecimal(valueStr)
        val divideValue = BigDecimal(divideStr)
        return fnum.format(value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP))
    }

    /**
     * 值比较大小
     * 如果valueStr大于等于compValueStr,则返回true,否则返回false
     *  true 代表可用余额不足
     * @param valueStr (需要消费金额)
     * @param compValueStr (可使用金额)
     */
    fun moneyComp(valueStr: BigDecimal, compValueStr: BigDecimal?): Boolean {
        //0:等于    >0:大于    <0:小于
        val result = valueStr.compareTo(compValueStr)
        return result >= 0
    }

    fun moneyComp(valueStr: String?, compValueStr: String?): Boolean {
        val value = BigDecimal(valueStr)
        val compValue = BigDecimal(compValueStr)
        //0:等于    >0:大于    <0:小于
        val result = value.compareTo(compValue)
        return result >= 0
    }

    /**
     * 给金额加逗号切割
     */
    fun moneyAddComma(str: String): String? {
        var str = str
        return try {
            var banNum = ""
            if (str.contains(".")) {
                val arr = str.split("\\.".toRegex()).toTypedArray()
                if (arr.size == 2) {
                    str = arr[0]
                    banNum = "." + arr[1]
                }
            }
            // 将传进数字反转
            val reverseStr = StringBuilder(str).reverse().toString()
            var strTemp = ""
            for (i in reverseStr.indices) {
                if (i * 3 + 3 > reverseStr.length) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length)
                    break
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ","
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length - 1)
            }
            // 将数字重新反转
            var resultStr = StringBuilder(strTemp).reverse().toString()
            resultStr += banNum
            resultStr
        } catch (e: Exception) {
            str
        }
    }

}