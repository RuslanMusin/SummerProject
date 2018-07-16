package com.summer.itis.summerproject.ui.tests.test_item.fragments.check_answers

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.CompoundButtonCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Answer
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.TEST_JSON
import com.summer.itis.summerproject.ui.tests.test_item.fragments.main.TestFragment
import com.summer.itis.summerproject.utils.Const
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_question.*
import java.util.ArrayList

class AnswersFragment : Fragment(), View.OnClickListener {

    private lateinit var question: Question
    private lateinit var test: Test
    private lateinit var type: String
    private var number: Int = 0

    private lateinit var colorStateList: ColorStateList

    private var textViews: MutableList<TextView>? = null
    private var checkBoxes: MutableList<CheckBox>? = null
    private var radioButtons: MutableList<RadioButton>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        type = arguments?.getString(ANSWERS_TYPE)!!
        val testStr: String = arguments?.getString(TEST_JSON)!!
        number = arguments?.getInt(QUESTION_NUMBER)!!
        test = gsonConverter.fromJson(testStr, Test::class.java)
        question = if(type.equals(RIGHT_ANSWERS)) test.rightQuestions[number] else test.wrongQuestions[number]

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        textViews = ArrayList()
        radioButtons = ArrayList()
        checkBoxes = ArrayList()

        tv_question.text = question.question

        setStartAnswers()
    }

    private fun setStartAnswers() {
        colorStateList = ColorStateList(
                arrayOf(intArrayOf(-android.R.attr.state_checked), // unchecked
                        intArrayOf(android.R.attr.state_checked))// checked
                ,
                intArrayOf(Color.parseColor("#FFFFFF"), Color.parseColor("#DC143C"))
        )



        for (answer in question.answers) {
            addAnswer(answer)
        }
        for(tv in textViews!!) {
            Log.d(Const.TAG_LOG,"text = " + tv.text)
        }

        if(number == (test.questions.size-1)) {
            btn_next_question.visibility = View.GONE
            btn_finish_questions.visibility = View.VISIBLE
        }
    }


    private fun setListeners() {
        btn_finish_questions!!.setOnClickListener(this)
        btn_next_question!!.setOnClickListener(this)
    }
    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_finish_questions -> {

                val args: Bundle = Bundle()
                args.putString(TEST_JSON, gsonConverter.toJson(test))
                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, TestFragment.newInstance(args))
                        .addToBackStack("AddQuestionFragment")
                        .commit()            }

            R.id.btn_next_question -> {
                val args: Bundle = Bundle()
                args.putString(TEST_JSON, gsonConverter.toJson(test))
                args.putInt(QUESTION_NUMBER, ++number)
                args.putString(ANSWERS_TYPE, type)
                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, AnswersFragment.newInstance(args))
                        .addToBackStack("AddQuestionFragment")
                        .commit()
            }

        }
    }

    private fun addAnswer(answer: Answer) {
        val view: LinearLayout = layoutInflater.inflate(R.layout.layout_item_question,li_answers,false) as LinearLayout
        val tvAnswer: TextView = view.findViewWithTag("tv_answer")
        tvAnswer.text = answer.text
        textViews?.add(tvAnswer)
        Log.d(Const.TAG_LOG,"text tv = ${tvAnswer.text}")
        val checkBox: CheckBox = view.findViewWithTag("checkbox")
        if(answer.isRight) {
            checkBox.isChecked = true
        }
        checkBoxes?.add(checkBox)
        Log.d(Const.TAG_LOG,"checkboxes size = ${checkBoxes?.size}")
        li_answers.addView(view)
        if(type.equals(WRONG_ANSWERS) && !answer.isRight && answer.userClicked != answer.isRight) {
            Log.d(TAG_LOG,"change checkbox color")
            checkBox.isChecked = true
            CompoundButtonCompat.setButtonTintList(checkBox, colorStateList)
        }
        checkBox.isEnabled = false
    }

    companion object {

        private val RESULT_LOAD_IMG = 0

        const val QUESTION_NUMBER = "queston_number"

        const val RIGHT_ANSWERS = "right_answers"
        const val WRONG_ANSWERS = "wrong_answers"
        const val ANSWERS_TYPE = "type_answers"
        const val CARD_JSON = "card_json"


        fun newInstance(args: Bundle): Fragment {
            val fragment = AnswersFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
