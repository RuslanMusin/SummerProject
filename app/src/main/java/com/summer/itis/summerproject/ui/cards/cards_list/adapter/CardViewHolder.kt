package com.summer.itis.summerproject.ui.cards.cards_list.adapter

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.summer.itis.summerproject.R
import android.content.Context
import android.support.annotation.NonNull
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.utils.ImageLoadHelper

/**
 * Created by Home on 10.07.2018.
 */

class CardViewHolder(itemView: View) : ViewHolder(itemView) {

    private var iv_photo: ImageView
    private var tv_name: TextView
    private var tv_desc: TextView

    companion object {
        fun create(context: Context): CardViewHolder {
            val view = View.inflate(context, R.layout.item_card, null)
            val holder = CardViewHolder(view)
            return holder
        }
    }

    init {
        iv_photo = itemView.findViewById(R.id.iv_portrait)
        tv_name = itemView.findViewById(R.id.tv_name)
        tv_desc = itemView.findViewById(R.id.tv_desc)
    }

    fun bind(@NonNull item: AbstractCard) {
        tv_name.setText(item?.name)
        tv_desc.setText(item?.description)
        if(item?.photoUrl != null) {
            ImageLoadHelper.loadPicture(iv_photo, item.photoUrl!!)
        }
    }
}