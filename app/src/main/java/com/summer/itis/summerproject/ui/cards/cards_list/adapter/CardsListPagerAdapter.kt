package com.summer.itis.summerproject.ui.cards.cards_list.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.summer.itis.summerproject.ui.cards.cards_list.tabs.AllCardsTabFragment
import com.summer.itis.summerproject.ui.cards.cards_list.tabs.MyCardsTabFragment

/**
 * Created by Home on 10.07.2018.
 */

class CardsListPagerAdapter(fm: FragmentManager,val numOfTabs: Int): FragmentPagerAdapter(fm){


    override fun getItem(position: Int): Fragment? {
        when (position){
            0 -> return AllCardsTabFragment.newInstance()
            1 -> return MyCardsTabFragment.newInstance()
            else -> {
                Log.d("CardsListPagerAdapter","can be 0 or 1, not this numbers")
                return null
            }
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "All"
            1 -> return "My"
            else -> {
                Log.d("CardsListPagerAdapter","can be 0 or 1, not this numbers")
                return null
            }
        }
    }
}