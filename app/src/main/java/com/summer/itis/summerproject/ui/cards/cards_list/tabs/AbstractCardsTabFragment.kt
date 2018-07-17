package com.summer.itis.summerproject.ui.cards.cards_list.tabs

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.ui.base.BaseAdapter
import com.summer.itis.summerproject.ui.cards.cards_list.CardsPresenter
import com.summer.itis.summerproject.ui.cards.cards_list.CardsView
import com.summer.itis.summerproject.ui.cards.cards_list.adapter.CardsListAdapter
import java.util.ArrayList

/**
 * Created by Home on 11.07.2018.
 */
abstract class AbstractCardsTabFragment : MvpAppCompatFragment(), CardsView, BaseAdapter.OnItemClickListener<AbstractCard>{

    protected lateinit var mRecyclerView: RecyclerView
    protected lateinit var mRecyclerViewAdapter: CardsListAdapter
    protected lateinit var cards: ArrayList<AbstractCard>
    @InjectPresenter
    lateinit var cardsPresenter : CardsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerViewAdapter = CardsListAdapter(ArrayList<AbstractCard>())
        mRecyclerView.adapter = mRecyclerViewAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerViewAdapter.setOnItemClickListener(this)
        return view
    }
}