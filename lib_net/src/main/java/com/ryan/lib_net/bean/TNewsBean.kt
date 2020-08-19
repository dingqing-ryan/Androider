package com.ryan.lib_net.bean

/**
 * @Author: Ryan
 * @Date: 2020/8/12 15:44
 * @Description: java类作用描述
 */
data class TNewsBean(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var resourcesId: Int
)