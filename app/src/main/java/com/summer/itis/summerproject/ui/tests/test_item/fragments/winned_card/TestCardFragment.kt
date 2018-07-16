package com.summer.itis.summerproject.ui.tests.test_item.fragments.winned_card

import QuestionFragment.Companion.ANSWERS_TYPE
import QuestionFragment.Companion.CARD_JSON
import QuestionFragment.Companion.RIGHT_ANSWERS
import QuestionFragment.Companion.WRONG_ANSWERS
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.TypedArrayUtils.getText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.R.id.*
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Comment
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.testRepository
import com.summer.itis.summerproject.ui.comment.CommentAdapter
import com.summer.itis.summerproject.ui.comment.OnCommentClickListener
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.TEST_JSON
import com.summer.itis.summerproject.ui.tests.test_item.fragments.check_answers.AnswersFragment
import com.summer.itis.summerproject.ui.tests.test_item.fragments.finish.FinishFragment
import com.summer.itis.summerproject.ui.tests.test_item.fragments.main.TestFragmentPresenter
import com.summer.itis.summerproject.ui.tests.test_item.fragments.main.TestFragmentView
import com.summer.itis.summerproject.ui.tests.test_list.test.TestListActivity
import com.summer.itis.summerproject.ui.widget.ExpandableTextView
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_finish_test.*
import kotlinx.android.synthetic.main.fragment_test_card.*
import kotlinx.android.synthetic.main.layout_test.*
import java.text.SimpleDateFormat
import java.util.*


class TestCardFragment: Fragment() {

    lateinit var test: Test
    lateinit var card: Card

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = TestCardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_test_card, container, false)

        val testStr: String? = arguments?.getString(TEST_JSON)
        test = gsonConverter.fromJson(testStr, Test::class.java)
        card = test.card!!
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