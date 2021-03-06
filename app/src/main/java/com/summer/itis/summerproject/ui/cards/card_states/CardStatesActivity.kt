package com.summer.itis.summerproject.ui.cards.card_states

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.FrameLayout
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.ui.base.EasyNavigationBaseActivity
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity
import com.summer.itis.summerproject.ui.cards.cards_states.CardsStatesPagerAdapter
import com.summer.itis.summerproject.utils.ApplicationHelper
import kotlinx.android.synthetic.main.activity_card_states.toolbar
import java.util.ArrayList

class CardStatesActivity : NavigationBaseActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerAdapter: CardsStatesPagerAdapter
    private lateinit var cards: ArrayList<Card>
    private lateinit var card: AbstractCard

    companion object {
        fun start(context: Context,card : AbstractCard){
            var intent = Intent(context,CardStatesActivity::class.java)
            intent.putExtra("CARD", card)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentFrameLayout = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(getContentLayout(), contentFrameLayout)

        card = intent.getParcelableExtra("CARD")
        mViewPager = findViewById(R.id.pager)
        mPagerAdapter = CardsStatesPagerAdapter(supportFragmentManager, ArrayList(), card)
        mViewPager.adapter = mPagerAdapter
        getCardsStates()
        setSupportActionBar(toolbar)
        setBackArrow(toolbar)
    }

    fun getContentLayout(): Int {
        return R.layout.activity_card_states
    }

    fun setCardsStates(cards: ArrayList<Card>){
        mPagerAdapter.setNewCards(cards)
    }

    fun getCardsStates(){
        RepositoryProvider
                .cardRepository
                .findMyAbstractCardStates(card?.id!!, ApplicationHelper.currentUser?.id!!)
                .subscribe({it -> setCardsStates(it as ArrayList<Card>)})
    }
}
