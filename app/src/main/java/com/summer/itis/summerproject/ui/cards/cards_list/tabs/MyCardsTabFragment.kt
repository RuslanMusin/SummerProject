package com.summer.itis.summerproject.ui.cards.cards_list.tabs

import android.content.Intent
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.ui.cards.cards_info.CardsActivity
import com.summer.itis.summerproject.ui.cards.cards_list.tabs.AbstractCardsTabFragment
import java.util.ArrayList

/**
 * Created by Home on 10.07.2018.
 */
class MyCardsTabFragment(): AbstractCardsTabFragment() {

    companion object {
        fun newInstance(): MyCardsTabFragment {
            val fragment = MyCardsTabFragment()
            return fragment
        }
    }

    override fun getCardsList(): ArrayList<Card> {
        //ПРОСТО ТЕСТОВЫЕ ДАННЫЕ ПОКА
        var cards = ArrayList<Card>()
        var card = Card()
        card.abstractCard?.name = "Name123123"
        card.abstractCard?.description = "asdjkf.adsnflansdlnfa.dsnf,madsf"
        var card1 = Card()
        card1.abstractCard?.name = "Name256756756756"
        card1.abstractCard?.description = "akjddfh;KSD;Fhsfylsiduyfilasdgfjanenanenenevbevenb"
        var card2 = Card()
        card2.abstractCard?.name = "Name31231"
        card2.abstractCard?.description = "akjddfh;KSD;Fhsfylsiduyfilasdgfjanenanenenevbevenb"
        cards.add(card)
        cards.add(card1)
        cards.add(card2)
        return cards
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, CardsActivity::class.java)
        intent.putExtra("POS", position)
        intent.putParcelableArrayListExtra("ARR", cards)
        activity?.startActivity(intent)
    }
}