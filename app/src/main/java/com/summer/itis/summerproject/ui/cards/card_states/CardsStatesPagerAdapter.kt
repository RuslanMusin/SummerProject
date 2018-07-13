package com.summer.itis.summerproject.ui.cards.cards_states

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.ui.cards.card_states.CardStatesFragment

/**
 * Created by Home on 11.07.2018.
 */
class CardsStatesPagerAdapter(fm : FragmentManager,var cards : ArrayList<Card>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return CardStatesFragment.newInstance(cards[position])
    }

    override fun getCount(): Int {
        return cards.size
    }
}