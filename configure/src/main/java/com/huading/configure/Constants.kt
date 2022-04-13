package com.huading.configure

import com.huading.configure.base.BaseApplication

object Constants {

    /**
     * netCode
     */
    var NET_CODE_FAIL: Int = 0
    var NET_CODE_SUCCESS = 200              //接口请求数据成功
    var NET_CODE_ERROR = 300                //接口请求数据失败
    var NET_CODE_PARSE_EXCEPTION = 400      //解析异常
    var NET_CODE_TOKEN_EXPIRED = 401        //token过期
    var NET_CODE_SERVER_EXCEPTION = 500     //接口异常
    var NET_CODE_COLLECTION_EXCEPTION = 600 //链接异常
    var NET_CODE_SCOKET_EXCEPTION = 1000    //scoket连接超时
    var NET_CODE_PASS_ERROR = 12024         //账号密码错误


    /**
     * key
     */
    object Key {

    }


    /**
     * Event
     */
    object Event {

    }


    /**
     * ResultCode
     */
    object ResultCode {

    }


    /**
     * 安装包名称
     */
    const val saveFileName = "MyConfigure.apk"


    /**
     * 下载包安装路径
     */
    var savePath: String = BaseApplication().sInstance.getExternalFilesDir(null).toString()

}