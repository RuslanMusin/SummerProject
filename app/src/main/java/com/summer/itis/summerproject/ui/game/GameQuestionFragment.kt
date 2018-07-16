
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.summer.itis.summerproject.model.Answer
import com.summer.itis.summerproject.model.Question

import java.util.ArrayList

import android.widget.*
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.TEST_JSON
import com.summer.itis.summerproject.ui.tests.test_item.fragments.finish.FinishFragment
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_question.*

class GameQuestionFragment : Fragment(), View.OnClickListener {

    private lateinit var question: Question
    private lateinit var test: Test
    private var number: Int = 0

    private var textViews: MutableList<TextView>? = null
    private var checkBoxes: MutableList<CheckBox>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val testStr: String = arguments?.getString(TEST_JSON)!!
        number = arguments?.getInt(QUESTION_NUMBER)!!
        test = gsonConverter.fromJson(testStr, Test::class.java)
        question = test.questions[number]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        textViews = ArrayList()
        checkBoxes = ArrayList()

        tv_question.text = question.question

        setStartAnswers()
    }

    private fun setStartAnswers() {

        for (answer in question.answers) {
            addAnswer(answer)
        }
    }


    private fun setListeners() {
        btn_next_question!!.setOnClickListener(this)
    }
    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_next_question -> {
                checkAnswers()
                val args: Bundle = Bundle()
                args.putString(TEST_JSON, gsonConverter.toJson(test))
                args.putInt(QUESTION_NUMBER, ++number)
                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, QuestionFragment.newInstance(args))
                        .addToBackStack("AddQuestionFragment")
                        .commit()
            }

        }
    }

    private fun checkAnswers() {
        question.userRight = true
        for(i in question.answers.indices) {
            val answer: Answer = question.answers[i]
                if(checkBoxes?.get(i)?.isChecked!!) {
                    answer.userClicked = true
                }
            if(answer.isRight && answer.userClicked != answer.isRight) {
                question.userRight = false
            }
        }
    }

    private fun addAnswer(answer: Answer) {
        val view: LinearLayout = layoutInflater.inflate(R.layout.layout_item_question,li_answers,false) as LinearLayout
        val tvAnswer: TextView = view.findViewWithTag("tv_answer")
        tvAnswer.text = answer.text
        textViews?.add(tvAnswer)
        val checkBox: CheckBox = view.findViewWithTag("checkbox")
        checkBoxes?.add(checkBox)
        li_answers.addView(view)
    }

    companion object {

        const val QUESTION_NUMBER = "queston_number"

        fun newInstance(args: Bundle): Fragment {
            val fragment = GameQuestionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
