package com.summer.itis.summerproject.ui.cards.cards_list.tabs

import android.content.Intent
import com.summer.itis.summerproject.model.Card
import java.util.ArrayList

/**
 * Created by Home on 10.07.2018.
 */
class AllCardsTabFragment(): AbstractCardsTabFragment(){

    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(): AllCardsTabFragment {
            val fragment = AllCardsTabFragment()
            return fragment
        }
    }

    override fun getCardsList(): ArrayList<Card> {
        var cards = ArrayList<Card>()
        var card = Card()
        card.abstractCard?.name = "Name"
        card.abstractCard?.description = "asdjkf.adsnflansdlnfa.dsnf,madsf"
        var card1 = Card()
        card1.abstractCard?.name = "Name2"
        card1.abstractCard?.description = "akjddfh;KSD;Fhsfylsiduyfilasdgfjanenanenenevbevenb"
        var card2 = Card()
        card2.abstractCard?.name = "Name3"
        card2.abstractCard?.description = "akjddfh;KSD;Fhsfylsiduyfilasdgfjanenanenenevbevenb"
        cards.add(card)
        cards.add(card1)
        cards.add(card2)
        return cards
    }

//    override fun onItemClick(position: Int) {
//        val intent = Intent(activity, CardsActivity::class.java)
//        intent.putExtra("POS", position)
//        intent.putParcelableArrayListExtra("ARR", cards)
//        activity?.startActivity(intent)
//    }
}