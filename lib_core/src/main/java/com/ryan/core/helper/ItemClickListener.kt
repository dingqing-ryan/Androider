package com.ryan.core.helper

/**
 * @Author: Ryan
 * @Date: 2020/8/20 11:00
 * @Description: 列表点击监听
 */
interface ItemClickListener<T> {
    fun onClick(data: T)
}