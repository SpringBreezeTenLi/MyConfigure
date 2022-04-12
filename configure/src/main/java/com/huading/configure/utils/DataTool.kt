package com.huading.configure.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * string，转换成任意类型的bean类
 */
inline fun <reified T> String.toBean(): T = Gson().fromJson(
        this,
        object : TypeToken<T>() {}.type
)

/**
 * 任意类转换json字符串
 */
inline fun Any.jsonToStr(): String = Gson().toJson(this)
