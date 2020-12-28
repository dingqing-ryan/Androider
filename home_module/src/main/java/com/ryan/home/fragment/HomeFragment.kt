package com.ryan.home.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ryan.core.activity.WebViewActivity
import com.ryan.core.activity.WebViewActivity.WEB_URL
import com.ryan.core.base.mvvm.BaseFragment
import com.ryan.core.helper.GlideImageLoader
import com.ryan.core.helper.ItemClickListener
import com.ryan.core.widget.banner.BannerConfig
import com.ryan.core.widget.banner.transformer.Transformer.ZoomOutSlide
import com.ryan.home.R
import com.ryan.home.adapter.ArticleListAdapter
import com.ryan.home.databinding.FragmentHomeBinding
import com.ryan.home.vm.HomeViewModel
import com.ryan.lib_net.bean.BannerBean
import com.ryan.lib_net.bean.TNewsBean
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 *
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), View.OnClickListener,
    ItemClickListener<TNewsBean>,
    OnRefreshListener, OnLoadMoreListener,
    ArticleListAdapter.ItemClickListener {

    private var imageData = mutableListOf<String>()
    private var titleData = mutableListOf<String>()

    override fun getLayoutId(inflater: LayoutInflater?, container: ViewGroup?): Int {
        return R.layout.fragment_home
    }

    override fun initViewObservable() {

    }

    override fun initView() {
        init()
        initBanner()
        mViewModel.getBanner().observe(this, Observer<List<BannerBean>?> { t ->
            val images = mutableListOf<String>()
            val titles = mutableListOf<String>()
            t?.forEach {
                images.add(it.imagePath)
                titles.add(it.title)
            }
            updateBannerData(images, titles)
        })

        mViewModel.getTNews().observe(this, Observer<List<TNewsBean>> { t ->

        })

        mViewModel.getArticles(true)
        finishRefresh()
    }

    /**
     * 初始化
     */
    private fun init() {
        mBinding.llMenu.setOnClickListener(this)
        mBinding.mySrl.setOnRefreshListener(this)
        mBinding.mySrl.setOnLoadMoreListener(this)
        mBinding.mySrl.autoRefresh()
        mBinding.lvArticle.adapter = mViewModel.articleListAdapter
        mBinding.rvPublic.adapter = mViewModel.tNewsAdapter
        mViewModel.tNewsAdapter?.setItemClickListener(this)
        val staggerManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        mBinding.rvPublic.layoutManager = staggerManager
        mBinding.indicator.bindRecyclerView(mBinding.rvPublic)
        mViewModel.articleListAdapter?.setListItemClickListener(this)
    }

    /**
     * 初始化Banner样式
     */
    private fun initBanner() {
        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            ?.setImageLoader(GlideImageLoader())
            ?.setBannerAnimation(ZoomOutSlide)
            ?.isAutoPlay(true)
            ?.setDelayTime(3000)
            ?.setIndicatorGravity(BannerConfig.RIGHT)
            ?.start()
    }

    /**
     * 刷新Banner
     */
    private fun updateBannerData(imgList: List<String>, titleList: List<String>) {
        imageData.clear()
        titleData.clear()
        imageData.addAll(imgList)
        titleData.addAll(titleList)
        mBinding.banner.update(imageData, titleData)
    }

    /**
     * 停止刷新
     */
    private fun finishRefresh() {
        mBinding.mySrl.finishLoadMore()
        mBinding.mySrl.finishRefresh()
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.ll_menu) {
            if (mBinding.drawer.isDrawerOpen(ll_left_content)) {
                mBinding.drawer.closeDrawer(ll_left_content)
            } else {
                mBinding.drawer.openDrawer(ll_left_content)
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.getArticles(true)
        finishRefresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mViewModel.getArticles(false)
        finishRefresh()
    }

    override fun onclickItem(url: String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra(WEB_URL, url)
        context?.startActivity(intent)
    }

    override fun onClick(data: TNewsBean) {
        startActivity(WebViewActivity::class.java)
    }
}
