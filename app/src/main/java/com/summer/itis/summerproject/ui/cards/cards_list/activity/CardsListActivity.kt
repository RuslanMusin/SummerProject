package com.summer.itis.summerproject.ui.cards.cards_list.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.ui.base.EasyNavigationBaseActivity
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity
import com.summer.itis.summerproject.ui.cards.cards_list.adapter.CardsListPagerAdapter
import kotlinx.android.synthetic.main.activity_cards_list.toolbar

class CardsListActivity : EasyNavigationBaseActivity() {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerAdapter: PagerAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CardsListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        supportActionBar(toolbar)
        setBackArrow(toolbar)
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_cards_list
    }

    private fun init(){
        mTabLayout = findViewById(R.id.tab_layout)
        mViewPager = findViewById(R.id.pager)
        mPagerAdapter = CardsListPagerAdapter(
                supportFragmentManager)
        mViewPager.adapter = mPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)
    }
}
