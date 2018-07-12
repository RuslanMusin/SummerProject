package com.summer.itis.summerproject.ui.cards.cards_list.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.ui.cards.cards_list.adapter.CardViewHolder
import com.summer.itis.summerproject.ui.cards.cards_list.adapter.CardsListAdapter
import com.summer.itis.summerproject.ui.cards.cards_list.OnItemClickListener
import java.util.ArrayList

/**
 * Created by Home on 11.07.2018.
 */
abstract class AbstractCardsTabFragment : Fragment(), OnItemClickListener {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mRecyclerViewAdapter: RecyclerView.Adapter<CardViewHolder>
    lateinit var cards: ArrayList<Card>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        mRecyclerView = view.findViewById(R.id.recycler_view)

        cards = getCardsList()

        mRecyclerViewAdapter = CardsListAdapter(cards, this)
        mRecyclerView.adapter = mRecyclerViewAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        return view

    }

    protected abstract fun getCardsList(): ArrayList<Card>
}