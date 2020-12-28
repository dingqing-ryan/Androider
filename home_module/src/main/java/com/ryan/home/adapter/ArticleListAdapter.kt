package com.ryan.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ryan.core.widget.RyanCornerImageView
import com.ryan.home.R
import com.ryan.lib_net.bean.ArticleBean

/**
 * @Author: Ryan
 * @Date: 2020/8/6 11:46
 * @Description: ListView 的首页文章Adapter
 */

class ArticleListAdapter(dl: List<ArticleBean>, mContext: Context) : BaseAdapter() {

    private var articles: List<ArticleBean>? = null
    private var mViewHolder: MyViewHolder? = null
    private var mContext: Context? = null
    private var itemClickListener: ItemClickListener? = null

    init {
        articles = dl
        this.mContext = mContext
    }

    fun setListItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    override fun getItem(position: Int): Any {
        return articles!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return articles?.size ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mConvertView = convertView
        if (convertView == null) {
            mConvertView = LayoutInflater.from(mContext).inflate(R.layout.articile_list_item, null)
            mViewHolder = MyViewHolder(mConvertView!!)
            mConvertView.tag = mViewHolder
        } else {
            mViewHolder = convertView.tag as MyViewHolder
        }

        if (articles?.size != 0) {
            if (articles!![position].shareUser.isEmpty()) {
                articles!![position].shareUser = "未署名"
            }
            mViewHolder?.tvTitle?.text =
                "：" + articles!![position].shareUser + "      分类│来源：" + articles!![position].superChapterName
            mViewHolder?.tvContent?.text = articles!![position].title + articles!![position].desc
            mViewHolder?.tvTime?.text = articles!![position].niceShareDate
            if (articles!![position].envelopePic.isNotEmpty()) {
                mViewHolder?.ryanCornerImageView?.visibility = View.VISIBLE
                Glide.with(mContext!!)
                    .load(articles!![position].envelopePic)
                    .fitCenter()
                    .into(mViewHolder?.ryanCornerImageView!!)
            } else {
                mViewHolder?.ryanCornerImageView?.visibility = View.GONE

            }
            mConvertView?.setOnClickListener { itemClickListener?.onclickItem(articles!![position].link) }
        }
        return mConvertView!!
    }

    interface ItemClickListener {
        fun onclickItem(url: String)
    }

    inner class MyViewHolder(view: View) {
        var tvTitle: TextView? = null
        var tvContent: TextView? = null
        var tvTime: TextView? = null
        var ryanCornerImageView: RyanCornerImageView? = null

        init {
            tvTitle = view.findViewById(R.id.tv_title)
            tvContent = view.findViewById(R.id.tv_content)
            tvTime = view.findViewById(R.id.tv_time)
            ryanCornerImageView = view.findViewById(R.id.rciv_article_logo)
        }
    }
}