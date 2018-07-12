package com.summer.itis.summerproject.ui.cards.cards_list.adapter

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.ui.cards.cards_list.OnItemClickListener

/**
 * Created by Home on 10.07.2018.
 */

class CardViewHolder(itemView: View, val onItemClickListener: OnItemClickListener) : ViewHolder(itemView) {

    var mImageView: ImageView
    var tv_name: TextView
    var tv_desc: TextView

    init {
        mImageView = itemView.findViewById(R.id.iv_portrait)
        tv_name = itemView.findViewById(R.id.tv_name)
        tv_desc = itemView.findViewById(R.id.tv_desc)
        itemView.setOnClickListener { onItemClickListener.onItemClick(adapterPosition) }
    }
}