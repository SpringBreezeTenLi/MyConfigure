package com.huading.configure.utils

import android.util.Log
import com.huading.configure.BuildConfig

object LogUtil {
    val TAG: String = "LogUtil"
    val isDebug: Boolean = BuildConfig.DEBUG

    fun d(TAG: String, s: String) {
        if (isDebug) Log.d(TAG, "-------------->$s")
    }

    fun d(s: String) {
        if (isDebug) Log.d(TAG, "-------------->$s")
    }

    fun e(TAG: String, s: String) {
        if (isDebug) Log.e(TAG, "-------------->$s")
    }

    fun e(s: String) {
        if (isDebug) Log.e(TAG, "-------------->$s")
    }

    fun i(TAG: String, s: String) {
        if (isDebug) Log.i(TAG, "-------------->$s")
    }

    fun i(s: String) {
        if (isDebug) Log.i(TAG, "-------------->$s")
    }

    fun printMap(name: String, map: Map<String, String>) {
        map.forEach {
            Log.i(TAG, "------${name}-------key: >${it.key}------value :${it.value}")
        }
    }

    fun printAnyMap(name: String, map: Map<String, Any>) {
        map.forEach {
            Log.i(TAG, "------${name}-------key: >${it.key}------value :${it.value}")
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    fun loge(tag: String?, msg: String?) {
        var msg = msg
        if (tag == null || tag.length == 0 || msg == null || msg.length == 0
        ) return
        val segmentSize = 3 * 1024
        val length = msg.length.toLong()
        if (length <= segmentSize) { // 长度小于等于限制直接打印
            Log.e(tag, msg)
        } else {
            while (msg!!.length > segmentSize) { // 循环分段打印日志
                val logContent = msg.substring(0, segmentSize)
                msg = msg.replace(logContent, "")
                Log.e(tag, "-------------------$logContent")
            }
            Log.e(tag, "-------------------$msg") // 打印剩余日志
        }
    }
}