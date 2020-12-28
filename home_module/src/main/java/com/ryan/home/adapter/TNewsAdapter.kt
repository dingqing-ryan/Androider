package com.ryan.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryan.core.helper.ItemClickListener
import com.ryan.core.widget.RyanCornerImageView
import com.ryan.home.R
import com.ryan.home.adapter.TNewsAdapter.MyViewHolder
import com.ryan.lib_net.bean.TNewsBean

class TNewsAdapter(newsList: List<TNewsBean>, context: Context) :
    RecyclerView.Adapter<MyViewHolder>() {
    private var mContext: Context? = null
    private var newsData: List<TNewsBean>? = null
    private var itemClickListener: ItemClickListener<TNewsBean>? = null

    init {
        newsData = newsList
        this.mContext = context
    }

    fun setItemClickListener(itemClickListener: ItemClickListener<TNewsBean>) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.t_news_list_item, viewGroup, false)
        val viewHolder = MyViewHolder(view)
        val width: Int = viewGroup.width
        viewHolder.itemView.layoutParams.width = (width / 4.5).toInt() //一行显示四个
        return viewHolder
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.tvName?.text = newsData?.get(i)?.name
        myViewHolder.ivAuthor?.setImageResource(newsData?.get(i)?.resourcesId!!)
        myViewHolder.itemView.setOnClickListener {
            itemClickListener?.onClick(newsData?.get(i)!!)
        }
    }

    override fun getItemCount(): Int {
        return newsData?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView? = itemView.findViewById(R.id.tv_name)
        var ivAuthor: RyanCornerImageView? = itemView.findViewById(R.id.iv_author)
    }
}