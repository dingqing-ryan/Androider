package com.ryan.lib_net.network

/**
 * Created by fanhenghao
 */
class HttpCodeException(code: Int, s: String) : Exception(s) {

    var code: Int = 0
        internal set

    init {
        this.code = code
    }
}
