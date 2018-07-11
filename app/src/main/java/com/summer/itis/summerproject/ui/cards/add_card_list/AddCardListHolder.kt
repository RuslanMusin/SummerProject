package com.summer.itis.summerproject.ui.cards.add_card_list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.pojo.opensearch.Item
import com.summer.itis.summerproject.utils.ImageLoadHelper


class AddCardListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView
    private val description: TextView
    private val imageView: ImageView

    init {
        name = itemView.findViewById(R.id.tv_name)
        description = itemView.findViewById(R.id.tv_description)
        imageView = itemView.findViewById(R.id.iv_cover)
    }

    fun bind(item: Item) {
        name.text = item.text!!.content
        val desc = item.description!!.content
        if (desc != null) {
            description.text = cutLongDescription(desc)
        } else {
            description.text = imageView.context.getText(R.string.description_default)
        }
        if (item.image != null) {
            /* if(items.getPhotoUrl().equals(String.valueOf(R.drawable.book_default))) {
                ImageLoadHelper.loadPictureByDrawableDefault(imageView,R.drawable.book_default);
            } else {
                ImageLoadHelper.loadPicture(imageView, items.getPhotoUrl());
            }*/
            ImageLoadHelper.loadPicture(imageView, item.image!!.source!!)

        }
    }

    private fun cutLongDescription(description: String): String {
        return if (description.length < MAX_LENGTH) {
            description
        } else {
            description.substring(0, MAX_LENGTH - MORE_TEXT.length) + MORE_TEXT
        }
    }

    companion object {

        private val MAX_LENGTH = 80
        private val MORE_TEXT = "..."

        fun create(context: Context): AddCardListHolder {
            val view = View.inflate(context, R.layout.item_cards, null)
            val holder = AddCardListHolder(view)
            return AddCardListHolder(view)
        }
    }
}
