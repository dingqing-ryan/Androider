package com.ryan.home.vm

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.ryan.core.base.mvvm.BaseViewModel
import com.ryan.home.R
import com.ryan.home.adapter.ArticleListAdapter
import com.ryan.home.adapter.TNewsAdapter
import com.ryan.lib_net.bean.ArticleBean
import com.ryan.lib_net.bean.ArticleListBean
import com.ryan.lib_net.bean.BannerBean
import com.ryan.lib_net.bean.TNewsBean
import com.ryan.lib_net.network.CommonObserver
import com.ryan.lib_net.network.ResultListModel
import com.ryan.lib_net.network.ResultModel
import com.ryan.lib_net.network.RetrofitFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Author: Ryan
 * @Date: 2020/8/11 17:02
 * @Description: HomePageFragment 的vm
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val articleData: ObservableArrayList<ArticleBean> = ObservableArrayList<ArticleBean>()
    private val newsData: ObservableArrayList<TNewsBean> = ObservableArrayList<TNewsBean>()
    val articleListAdapter: ArticleListAdapter? = ArticleListAdapter(articleData, application)
    val tNewsAdapter: TNewsAdapter? = TNewsAdapter(newsData, application)

    override fun onBundle(bundle: Bundle?) {

    }

    /**
     * 获取Banner数据
     */
    fun getBanner(): MutableLiveData<List<BannerBean>> {
        val bannerData: MutableLiveData<List<BannerBean>> = MutableLiveData<List<BannerBean>>()
        RetrofitFactory.instance.getWanBanner()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.onErrorReturn { t: Throwable -> ResultListModel(-1, t.message!!) }
            ?.subscribe(object :
                CommonObserver<ResultListModel<BannerBean>?>(getApplication(), false) {
                override fun success(data: ResultListModel<BannerBean>?) {
                    if (data?.errorCode == 0) {
                        bannerData.value = data.data
                    }
                }

                override fun error(e: Throwable) {

                }
            })
        return bannerData
    }

    /**
     * 获取首页文章
     */
    fun getArticles(isFresh: Boolean) {
        if (isFresh) {
            mPage.set(0)
            articleData.clear()
        }

        RetrofitFactory.instance.getArticleList(mPage.get())
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.onErrorReturn { t -> ResultModel(-1, t.message!!) }
            ?.subscribe(object :
                CommonObserver<ResultModel<ArticleListBean>?>(getApplication(), false) {
                override fun success(data: ResultModel<ArticleListBean>?) {
                    if (data?.errorCode == 0) {
                        articleData.addAll(data.data?.datas!!)
                        articleListAdapter?.notifyDataSetChanged()
                        setPage(articleData, isFresh)
                    }
                }

                override fun error(e: Throwable?) {

                }
            })
    }

    /**
     * 获取公众号列表
     */
    fun getTNews(): MutableLiveData<List<TNewsBean>> {
        val tNewsData: MutableLiveData<List<TNewsBean>> = MutableLiveData<List<TNewsBean>>()
        RetrofitFactory.instance.getTNews()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.onErrorReturn { t: Throwable -> ResultListModel(-1, t.message!!) }
            ?.subscribe(object : CommonObserver<ResultListModel<TNewsBean>?>(getApplication(), false) {
                override fun success(data: ResultListModel<TNewsBean>?) {
                    if (data?.errorCode == 0) {
                        tNewsData.value = data.data
                        data.data!!.forEach { n ->
                            when (n.name) {
                                "鸿洋" -> {
                                    n.resourcesId = R.drawable.public_hy
                                }
                                "郭霖" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                                "玉刚说" -> {
                                    n.resourcesId = R.drawable.public_ygs
                                }
                                "承香墨影" -> {
                                    n.resourcesId = R.drawable.public_cxmy

                                }
                                "Android群英传" -> {
                                    n.resourcesId = R.drawable.public_qyz
                                }
                                "鸿阳" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                                "code小生" -> {
                                    n.resourcesId = R.drawable.public_code
                                }
                                "谷歌开发者" -> {
                                    n.resourcesId = R.drawable.public_ggkfz
                                }
                                "奇卓社" -> {
                                    n.resourcesId = R.drawable.public_qzs
                                }
                                "美团技术团队" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                                "GcsSloop" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                                "互联网侦察" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                                "susion随心" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                                "程序亦非猿" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                                "Gityuan" -> {
                                    n.resourcesId = R.drawable.public_guolin
                                }
                            }
                        }
                        newsData.addAll(data.data!!)
                        tNewsAdapter?.notifyDataSetChanged()
                    }
                }

                override fun error(e: Throwable) {

                }
            })
        return tNewsData
    }
}