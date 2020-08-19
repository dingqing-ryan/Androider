package com.ryan.core.utils;

import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2020/8/11 16:32
 * @Description:  list辅助工具类
 */
public class ListUtils {
    /**
     * 判断list 是否为空
     * @param list
     * @param <T>
     * @return
     */
    public static  <T> boolean isListNotNull(List<T> list){
        if(list!=null&&!list.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 判断list 是否为空
     * @param list
     * @param <T>
     * @return
     */
    public static  <T> boolean isListNull(List<T> list){
        if(list!=null&&!list.isEmpty()){
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.equals("null");
    }

    public static boolean isNoEmpty(String s) {
        boolean bool=s == null || s.length() == 0 || s.equals("null");
        return !bool;
    }
}
