package com.ryan.lib_net.bean

/**
 * @Author: Ryan
 * @Date: 2020/8/5 14:53
 * @Description: java类作用描述
 */
data class BannerBean(

    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)
