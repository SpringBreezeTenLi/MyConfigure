package com.huading.configure.utils

import android.content.Context
import android.widget.Toast

var mToast: Toast? = null

fun toast(message: String, context: Context) {
    if (mToast != null) {
        mToast?.cancel()
    }
    mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    mToast?.setText(message)
    mToast?.show()
}