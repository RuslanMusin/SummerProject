package com.summer.itis.summerproject.ui.tests.test_item.fragments.finish

import QuestionFragment.Companion.QUESTION_NUMBER
import QuestionFragment.Companion.RIGHT_ANSWERS
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView
import com.summer.itis.summerproject.ui.tests.add_test.fragments.question.AddQuestionFragment
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity
import com.summer.itis.summerproject.ui.tests.test_list.test.TestListActivity
import com.summer.itis.summerproject.utils.Const
import kotlinx.android.synthetic.main.fragment_finish_test.*
import kotlinx.android.synthetic.main.fragment_question.*

class FinishFragment : Fragment(), View.OnClickListener {

    var number: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_finish_test, container, false)

        number = savedInstanceState?.getInt(RIGHT_ANSWERS)!!

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_right_answers.text = number.toString()
        btn_finish_questions.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                TestListActivity.start(activity as Activity)
            }
        })



        super.onViewCreated(view, savedInstanceState)
    }




    override fun onClick(v: View?) {

    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = FinishFragment()
            fragment.arguments = args
            return fragment
        }
    }
}