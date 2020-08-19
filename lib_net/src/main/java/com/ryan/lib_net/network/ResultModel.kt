package com.ryan.lib_net.network


/**
 * 返回值 返回值 data节点为 object
 */
class ResultModel<T>(var errorCode: Int, var errorMsg: String)  {

    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }

}
