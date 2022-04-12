package com.huading.configure.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * ViewModel基类
 */
open abstract class BaseViewModel : ViewModel() {

    private val mViewModelJob = SupervisorJob()
    val uiScope = CoroutineScope(Dispatchers.Main + mViewModelJob)
    val errorLive by lazy {
        MutableLiveData<String>()
    }

    override fun onCleared() {
        super.onCleared()
        mViewModelJob.cancel()
    }

    //添加　请求框加载
    val START = 0
    val END = 1
    val TOKEN_EXPRIED = 2

    val stateLiveData: MutableLiveData<Int> = MutableLiveData()

    val stateNetData: MutableLiveData<Int> = MutableLiveData()

    fun start() {
        stateLiveData.postValue(START)
    }

    fun end() {
        stateLiveData.postValue(END)
    }

    fun tokenExpried() {
        stateNetData.postValue(TOKEN_EXPRIED)
    }

}