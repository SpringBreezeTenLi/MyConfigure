package com.huading.configure.base

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class BaseApplication : MultiDexApplication() {

    lateinit var sInstance:BaseApplication

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        sInstance = this
        MultiDex.install(this)
    }

}