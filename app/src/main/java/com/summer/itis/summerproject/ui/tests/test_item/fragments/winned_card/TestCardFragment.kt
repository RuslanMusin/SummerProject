package com.summer.itis.summerproject.ui.tests.test_item.fragments.winned_card

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.ui.base.BaseBackActivity
import com.summer.itis.summerproject.ui.base.OnBackPressedListener
import com.summer.itis.summerproject.ui.tests.ChangeToolbarListener
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.FINISH_FRAGMENT
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.TEST_JSON
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.WINNED_FRAGMENT
import com.summer.itis.summerproject.ui.tests.test_item.fragments.finish.FinishFragment
import com.summer.itis.summerproject.ui.tests.test_item.fragments.main.TestFragment
import com.summer.itis.summerproject.ui.widget.ExpandableTextView
import com.summer.itis.summerproject.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_test_card.*


class TestCardFragment: Fragment(), OnBackPressedListener {


    lateinit var test: Test
    lateinit var card: Card

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = TestCardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onBackPressed() {
        val args: Bundle = Bundle()
        args.putString(TEST_JSON, gsonConverter.toJson(test))
        val fragment = FinishFragment.newInstance(args)
        (activity as BaseBackActivity).changeFragment(fragment, FINISH_FRAGMENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_test_card, container, false)

        val testStr: String? = arguments?.getString(TEST_JSON)
        test = gsonConverter.fromJson(testStr, Test::class.java)
        card = test.card!!
        (activity as BaseBackActivity).currentTag = TestActivity.WINNED_FRAGMENT
        (activity as ChangeToolbarListener).changeToolbar(WINNED_FRAGMENT,"Карта ${card.abstractCard?.name}")
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (card_description as ExpandableTextView).text = card.abstractCard?.description
        card_name.text = card.abstractCard?.name
        tv_support.text = tv_support.text.toString() + " :  " + card.support.toString()
        tv_hp.text = tv_hp.text.toString() + " :  " + card.hp.toString()
        tv_strength.text = tv_strength.text.toString() + " :  " + card.strength.toString()
        tv_prestige.text = tv_prestige.text.toString() + " :  " + card.prestige.toString()
        tv_intelligence.text = tv_intelligence.text.toString() + " :  " + card.intelligence.toString()

        card.abstractCard?.photoUrl?.let {
            Glide.with(card_image.context)
                    .load(it)
                    .into(card_image)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}