package com.ryan.lib_net.bean

import android.os.Parcel

/**
 *
 * 主页数据文章列表数据结构
 */

class ArticleListBean() {

    /**
     * curPage : 2
     * datas : [{"apkLink":"","author":"lanzry","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2674,"link":"http://www.jianshu.com/p/5a272afb4c2e","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521383343000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"一年经验-有赞面经","type":0,"visible":1,"zan":0}]
     */

    var curPage: Int = 0
    var datas: MutableList<ArticleBean>? = null

    constructor(parcel: Parcel) : this() {
        curPage = parcel.readInt()
    }
}
