package com.summer.itis.summerproject.ui.cards.cards_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.ui.base.EasyNavigationBaseActivity
import kotlinx.android.synthetic.main.activity_cards.toolbar
import java.util.ArrayList

class CardsActivity : EasyNavigationBaseActivity() {

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
        val card = intent.getParcelableExtra<AbstractCard>("CARD")
        cards = intent.getParcelableArrayListExtra("CARDS")
        var pos = getPosOfCard(card)
        mViewPager = findViewById(R.id.pager)
        mPagerAdapter = CardsPagerAdapter(supportFragmentManager,cards,intent.getStringExtra("TAG"))
        mViewPager.adapter = mPagerAdapter
        mViewPager.setCurrentItem(pos)
        mPagerAdapter.notifyDataSetChanged()
        supportActionBar(toolbar)
        setBackArrow(toolbar)
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_cards
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
