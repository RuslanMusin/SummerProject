package com.summer.itis.summerproject.ui.cards.cards_list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.summer.itis.summerproject.R.layout
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.ui.cards.cards_list.OnItemClickListener

/**
 * Created by Home on 10.07.2018.
 */
class CardsListAdapter(var list: List<Card>, var mOnItemClickListener: OnItemClickListener) :
        RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(layout.item_card, parent, false), mOnItemClickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = list[position]
        holder.tv_name.setText(card.abstractCard?.name)
        holder.tv_desc.setText(card.abstractCard?.description)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}