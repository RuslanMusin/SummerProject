package com.summer.itis.summerproject.ui.cards.cards_info

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.ui.base.BaseActivity
import java.util.ArrayList

class CardsActivity : BaseActivity() {

    lateinit var mViewPager: ViewPager
    lateinit var mPagerAdapter: PagerAdapter
    lateinit var mCards: ArrayList<Card>
    var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        val intent = intent
        mCards = intent.getParcelableArrayListExtra<Card>("ARR")
        pos = intent.getIntExtra("POS", 0)

        mViewPager = findViewById(R.id.pager)
        mPagerAdapter = CardsPagerAdapter(supportFragmentManager, mCards)


        mViewPager.adapter = mPagerAdapter
        mPagerAdapter.notifyDataSetChanged()
        mViewPager.currentItem = pos
    }
}
