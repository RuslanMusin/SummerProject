package com.summer.itis.summerproject.ui.cards.cards_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.ui.base.BaseActivity
import java.text.FieldPosition
import java.util.ArrayList

class CardsActivity : BaseActivity() {

    lateinit var mViewPager: ViewPager
    lateinit var mPagerAdapter: CardsPagerAdapter
    lateinit var cards: ArrayList<AbstractCard>

    companion object {
        fun start(activity: Context,card: AbstractCard,cards : ArrayList<AbstractCard>,tag : String){
            val intent = Intent(activity, CardsActivity::class.java)
            intent.putExtra("CARD", card)
            intent.putParcelableArrayListExtra("CARDS", cards)
            intent.putExtra("TAG", tag)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        val card = intent.getParcelableExtra<AbstractCard>("CARD")
        cards = intent.getParcelableArrayListExtra("CARDS")
        var pos = getPosOfCard(card)
        mViewPager = findViewById(R.id.pager)
        mPagerAdapter = CardsPagerAdapter(supportFragmentManager,cards,intent.getStringExtra("TAG"))
        mViewPager.adapter = mPagerAdapter
        mViewPager.setCurrentItem(pos)
        mPagerAdapter.notifyDataSetChanged()
    }

    private fun getPosOfCard(card: AbstractCard): Int{
        var pos: Int = 0
        for(x in cards){
            if(x.id == card.id){
                pos = cards.indexOf(x)
            }
        }
        return pos
    }
}
