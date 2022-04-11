package com.huading.configure.base

class BaseEvent<T>(val type: Int, val msg: String, val info: T?) {}