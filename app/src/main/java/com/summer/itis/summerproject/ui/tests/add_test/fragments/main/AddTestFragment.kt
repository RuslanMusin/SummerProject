package com.summer.itis.summerproject.ui.tests.add_test.fragments.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.ui.cards.add_card.AddCardActivity.Companion.CARD_EXTRA
import com.summer.itis.summerproject.ui.cards.add_card_list.AddCardListActivity
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView
import com.summer.itis.summerproject.ui.tests.add_test.fragments.question.AddQuestionFragment

import com.summer.itis.summerproject.utils.Const.COMA
import com.summer.itis.summerproject.utils.Const.gsonConverter

class AddTestFragment : Fragment(), View.OnClickListener {

    private var imageUri: Uri? = null

    private var tiTestName: TextInputLayout? = null
    private var tiTestDesc: TextInputLayout? = null
    private var btnAddCard: Button? = null
    private var tvAddedCards: TextView? = null
    private var btnCreateQuestions: Button? = null

    private var etTestName: EditText? = null
    private var etTestDesc: EditText? = null

    private var addTestView: AddTestView? = null
    private var test: Test? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_test, container, false)
        addTestView = activity as AddTestView?
        test = Test()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        etTestDesc = view.findViewById(R.id.et_test_desc)
        etTestName = view.findViewById(R.id.et_test_name)
        tiTestName = view.findViewById(R.id.ti_test_name)
        tiTestDesc = view.findViewById(R.id.ti_test_desc)
        btnAddCard = view.findViewById(R.id.btn_add_card)
        btnCreateQuestions = view.findViewById(R.id.btn_create_questions)
        tvAddedCards = view.findViewById(R.id.tv_added_cards)
    }

    private fun setListeners() {
        btnCreateQuestions!!.setOnClickListener(this)
        btnAddCard!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_create_questions -> {
                test!!.title = etTestName!!.text.toString()
                test!!.desc = etTestDesc!!.text.toString()

                addTestView!!.setTest(test!!)

                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, AddQuestionFragment.newInstance())
                        .addToBackStack("AddTestFragment")
                        .commit()
            }

            R.id.btn_add_card -> {
                val intent = Intent(activity, AddCardListActivity::class.java)
                startActivityForResult(intent, ADD_CARD)
            }
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if (reqCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK) {
            imageUri = data!!.data
        }

        if (reqCode == ADD_CARD && resultCode == Activity.RESULT_OK) {
            val card = gsonConverter.fromJson(data!!.getStringExtra(CARD_EXTRA), Card::class.java)
            tvAddedCards!!.text = tvAddedCards!!.text.toString() + COMA + card.abstractCard?.name
            test!!.card = card
        }
    }

    companion object {

        private val RESULT_LOAD_IMG = 0
        private val ADD_CARD = 1

        fun newInstance(): Fragment {
            val fragment = AddTestFragment()
            return AddTestFragment()
        }
    }
}
