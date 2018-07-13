package com.summer.itis.summerproject.ui.cards.card_states

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.ui.base.BaseActivity
import com.summer.itis.summerproject.ui.cards.cards_states.CardsStatesPagerAdapter
import java.util.ArrayList

class CardStatesActivity : BaseActivity() {

    lateinit var mViewPager: ViewPager
    lateinit var mPagerAdapter: PagerAdapter
    lateinit var mCards: ArrayList<Card>

    companion object {
        fun start(context: Context){
            var intent = Intent(context,CardStatesActivity.javaClass)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_states)

        mViewPager = findViewById(R.id.pager)
        mPagerAdapter = CardsStatesPagerAdapter(supportFragmentManager, mCards)
        mViewPager.adapter = mPagerAdapter
    }
}