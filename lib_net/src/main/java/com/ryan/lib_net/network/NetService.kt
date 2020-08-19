package com.ryan.lib_net.network


import com.ryan.lib_net.bean.ArticleListBean
import com.ryan.lib_net.bean.BannerBean
import com.ryan.lib_net.bean.TNewsBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author: Ryan
 * @Date: 2020/8/6 16:19
 * @Description: App所有网络接口
 */
interface NetService {

    /**
     * 首页Banner
     */
    @GET("/banner/json")
    fun getWanBanner(): Observable<ResultListModel<BannerBean>?>?


    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/chapters/json")
    fun getTNews(): Observable<ResultListModel<TNewsBean>?>?

    /**
     * 获取主页文章
     */
    @GET("/article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<ResultModel<ArticleListBean>>
}
