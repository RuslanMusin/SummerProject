package com.summer.itis.summerproject.ui.tests.test_list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide

import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.ImageLoadHelper

class TestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView
    private val description: TextView
    private val imageView: ImageView

    init {
        name = itemView.findViewById(R.id.tv_name)
        description = itemView.findViewById(R.id.tv_description)
        imageView = itemView.findViewById(R.id.iv_cover)
    }

    fun bind(item: Test) {
        name.text = item.title

        description.text = item.desc?.let { cutLongDescription(it) }

        if (item.imageUrl != null) {
            /*  if (item.imageUrl == R.drawable.ic_person_black_24dp.toString()) {
                  ImageLoadHelper.loadPictureByDrawableDefault(imageView, R.drawable.ic_person_black_24dp)
              } else {
                  //                ImageLoadHelper.loadPicture(imageView, item.getPhotoUrl());
                  val imageReference = ApplicationHelper.storageReference.child(item.imageUrl!!)

                  Glide.with(imageView.context)
                          .load(imageReference)
                          .into(imageView)
              }*/
            Glide.with(imageView.context)
                    .load(item.imageUrl)
                    .into(imageView)

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

        fun create(parent: ViewGroup): TestItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_books, parent, false);
//            val view = View.inflate(context, R.layout.item_books, null )
            val holder = TestItemHolder(view)
            return holder
        }
    }
}
