package com.summer.itis.summerproject.ui.cards.cards_list.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.ui.base.BaseAdapter
import com.summer.itis.summerproject.ui.cards.cards_info.CardsActivity
import java.util.ArrayList

/**
 * Created by Home on 10.07.2018.
 */
class MyCardsTabFragment(): AbstractCardsTabFragment(){

    override fun showDetails(card: AbstractCard) {
        CardsActivity.start(context!!,card,cards,"My")
    }

    override fun onItemClick(item: AbstractCard) {
        cardsPresenter.onItemClick(item)
    }

    override fun showItems(items: List<AbstractCard>) {
        cards = items as ArrayList<AbstractCard>
        mRecyclerViewAdapter.changeDataSet(items)
    }

    override fun handleError(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(): MyCardsTabFragment {
            val fragment = MyCardsTabFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = super.onCreateView(inflater, container, savedInstanceState)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardsPresenter.getUserAbstractCardsList()
    }
}