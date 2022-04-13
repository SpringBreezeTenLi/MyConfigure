package com.huading.configure

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Environment
import com.blankj.utilcode.util.AppUtils
import com.huading.configure.base.BaseApplication


/**
 * 配置文件
 */

class AppConfig private constructor() {

    private object SingleHolder {
        val sInstance = AppConfig()
    }

    companion object {
        @JvmStatic
        val instance: AppConfig
            get() = SingleHolder.sInstance
    }

    /**
     * 外部sd卡
     */
    val DCMI_PATH =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

    /**
     * 内部存储
     * /data/data/<application package>/files目录
     */
    val INNER_PATH: String =
        BaseApplication().sInstance.filesDir.absolutePath

    /**
     * 文件夹名字
     */
    val DIR_NAME = AppUtils.getAppName()

    /**
     * log保存路径
     */
    val LOG_PATH: String = "$DCMI_PATH/$DIR_NAME/log/"

    /**
     * 域名
     */
    var HOST: String = getHost()

    /**
     * 获取域名
     */
    fun getHost(): String {
        if (BuildConfig.DEBUG) {
            return getMetaDataString("SERVER_DEBUG_HOST")
        } else {
            return getMetaDataString("SERVER_HOST")
        }
    }

    /**
     * 根据key  获取清单文件的 值
     */
    fun getMetaDataString(key: String): String {
        var res: String = ""
        try {
            val appInfo: ApplicationInfo = BaseApplication().sInstance.packageManager
                .getApplicationInfo(
                    BaseApplication().sInstance.packageName,
                    PackageManager.GET_META_DATA
                )
            res = appInfo.metaData.getString(key).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return res
    }

    /**
     * 根据key  获取清单文件的 值
     */
    fun getMetaDataInt(key: String): Int {
        var res: Int = 0
        try {
            val appInfo: ApplicationInfo = BaseApplication().sInstance.packageManager
                .getApplicationInfo(
                    BaseApplication().sInstance.packageName,
                    PackageManager.GET_META_DATA
                )
            res = appInfo.metaData.getInt(key)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return res
    }

    /**
     * 根据key  获取清单文件的 值
     */
    fun getMetaDataBoolean(key: String): Boolean {
        var res: Boolean = false
        try {
            val appInfo: ApplicationInfo = BaseApplication().sInstance.packageManager
                .getApplicationInfo(
                    BaseApplication().sInstance.packageName,
                    PackageManager.GET_META_DATA
                )
            res = appInfo.metaData.getBoolean(key)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return res
    }

    /**
     * 获取versionCode
     */
    val versionCode: Int
        get() {
            try {
                return BaseApplication().sInstance.packageManager
                    .getPackageInfo(BaseApplication().sInstance.packageName, 0).versionCode
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            } catch (e1: PackageManager.NameNotFoundException) {
                e1.printStackTrace()
            }
            return 0
        }

    /**
     * 获取versionName
     */
    val versionName: String
        get() {
            try {
                return BaseApplication().sInstance.packageManager
                    .getPackageInfo(BaseApplication().sInstance.packageName, 0).versionName
            } catch (e1: NumberFormatException) {
                e1.printStackTrace()
            } catch (e1: PackageManager.NameNotFoundException) {
                e1.printStackTrace()
            }
            return "1.0"
        }

    private var lastClickTime: Long = 0
    private val minClickDelayTime = 1000

    fun isFastClick(): Boolean {
        var flag = false
        val curClickTime = System.currentTimeMillis()
        if (curClickTime - lastClickTime >= minClickDelayTime) {
            flag = true
        }
        lastClickTime = curClickTime
        return flag
    }

}