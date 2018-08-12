package com.summer.itis.summerproject.ui.game.play.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card

class GameCardsListAdapter(
        val items: ArrayList<Card>,
        val context: Context,
        val onClick: (card: Card) -> Unit) :
        RecyclerView.Adapter<GameCardsListViewHolder>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardsListViewHolder {
        return GameCardsListViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_game_card_small, parent, false))
    }


    override fun onBindViewHolder(holder: GameCardsListViewHolder, position: Int) {
//        holder.nameView.text = items[position].abstractCard!!.name
        Glide.with(context)
                .load(items[position].abstractCard!!.photoUrl)
                .into(holder.image)

        holder.itemView.setOnClickListener {
            val pos = holder.adapterPosition

            onClick(items[pos])

            //TODO delete selected in presenter?

            notifyItemRemoved(pos)
            items.removeAt(pos)
        }
    }

}
