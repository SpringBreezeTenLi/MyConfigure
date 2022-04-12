package com.huading.configure.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class TimeUtil {

    /**
     * 将字符串转为时间戳
     */
    fun getStringToDate(time: String): Long {
        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date: Date = Date()
        try {
            date = formatter.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.time
    }

    /**
     * 验证日期格式是否合法
     */
    fun checkDate(a: String): Boolean {
        val c =
            "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$"
        val pattern = Pattern.compile(c)
        val matcher = pattern.matcher(a)
        return matcher.matches()
    }

    /**
     * 根据时间戳获取 0、年  1、月  2、日  3、时  4、时分
     */
    fun getDateStr(time: Long, type: Int): String {
        var string = ""
        when (type) {
            0 -> {
                string = "yyyy"
            }
            1 -> {
                string = "MM"
            }
            2 -> {
                string = "dd"
            }
            3 -> {
                string = "HH"
            }
            4 -> {
                string = "HH:mm"
            }
        }
        val simpleDateFormat = SimpleDateFormat(string)
        val date = Date(time)
        return simpleDateFormat.format(date)
    }

    /**
     * 失效日期计算
     * 年月日
     */
    fun getDateByType(releaseDate: String?, num: Int, type: Int): String? {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        var d: Date? = Date()
        try {
            calendar.time = df.parse(releaseDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var num1: Int = 0
        when (type) {
            1 -> {
                num1 = calendar[Calendar.YEAR]
                calendar[Calendar.YEAR] = num1 + num
            }
            2 -> {
                num1 = calendar[Calendar.MONTH]
                calendar[Calendar.MONTH] = num1 + num
            }
            3 -> {
                num1 = calendar[Calendar.DAY_OF_YEAR]
                calendar[Calendar.DAY_OF_YEAR] = num1 + num
            }
        }
        d = calendar.time
        return df.format(d)
    }

}