package com.ryan.lib_net.network

/**
 * @Author: Ryan
 * @Date: 2020/8/5 17:00
 * @Description: java类作用描述
 */
class ResultListModel<T>(var errorCode: Int, var errorMsg: String) {
    var data: List<T>? = null
}