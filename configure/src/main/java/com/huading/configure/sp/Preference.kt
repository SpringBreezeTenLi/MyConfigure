package com.huading.configure.sp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.blankj.utilcode.util.LogUtils
import com.huading.configure.AppConfig
import com.huading.configure.base.BaseApplication
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.jvm.Throws
import kotlin.reflect.KProperty

class Preference<T>() {

    private var keyName: String? = null
    private var defaultValue: T? = null

    constructor(keyName: String,defaultValue: T) : this() {
        this.keyName = keyName
        this.defaultValue = defaultValue
    }

    private val prefs: SharedPreferences by lazy {
        BaseApplication().sInstance.applicationContext.getSharedPreferences(AppConfig.instance.DIR_NAME, Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T  {
        return findSharedPreference(keyName!!, defaultValue!!)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharedPreferences(keyName!!, value)
    }

    /**
     * 查找数据 返回一个具体的对象
     * 没有查找到value 就返回默认的序列化对象，然后经过反序列化返回
     */
    fun  findSharedPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default) as String
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> deSerialization(getString(name, serialize(default)) as String)
        }
        res as T
    }

    @SuppressLint("CommitPrefEdits")
    fun  putSharedPreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
    }

    /**
     * 删除全部数据
     */
    fun clear() {
        LogUtils.e("SP info", "调用$this clear()")
        prefs.edit().clear().commit()
    }

    /**
     * 根据key删除存储数据
     */
    fun remove(key: String) {
        LogUtils.e("SP info", "调用$this remove()参数值为：$key")
        prefs.edit().remove(key).commit()
    }

    /**
     * 序列化对象
     */
    @Throws(Exception::class)
    private fun <T> serialize(obj: T): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
            byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象
     */
    @Throws(Exception::class)
    private fun <T> deSerialization(str: String): T {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream)
        val obj = objectInputStream.readObject() as T
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

    /****
     * 使用
     *  getValue
     *  private var numCodeValue by Preference("numCode_key", 0)
     *
     *  putValue
     *  numCodeValue = 1000
     *
     *  获取 SharedPreferences
     *  val prefs: Preference<String> = Preference()
     *
     *  remove
     *  prefs.remove("keyname")
     *
     *  clear
     *  prefs.clear()
     *  clear 之后再获取， 获取的值是默认值
     */
}