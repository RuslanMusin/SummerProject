
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jaredrummler.materialspinner.MaterialSpinner
import com.summer.itis.summerproject.model.Answer
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView

import java.util.ArrayList

import android.app.Activity.RESULT_OK
import android.widget.*
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity
import com.summer.itis.summerproject.ui.tests.add_test.fragments.question.AddQuestionFragment
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.TEST_JSON
import com.summer.itis.summerproject.ui.tests.test_item.fragments.finish.FinishFragment
import com.summer.itis.summerproject.utils.Const.TEST_MANY_TYPE
import com.summer.itis.summerproject.utils.Const.TEST_ONE_TYPE
import com.summer.itis.summerproject.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : Fragment(), View.OnClickListener {

    private lateinit var question: Question
    private lateinit var test: Test
    private var number: Int = 0
    private var rights: Int = 0


    private var answers: MutableList<Answer>? = null

    private var editTexts: MutableList<TextView>? = null
    private var checkBoxes: MutableList<CheckBox>? = null
    private var radioButtons: MutableList<RadioButton>? = null

    private lateinit var liParams: LinearLayout.LayoutParams
    private lateinit var tiParams: LinearLayout.LayoutParams
    private lateinit var etParams: LinearLayout.LayoutParams
    private lateinit var rbParams: LinearLayout.LayoutParams


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val testStr: String = arguments?.getString(TEST_JSON)!!
        number = arguments?.getInt(QUESTION_NUMBER)!!
        rights = arguments?.getInt(RIGHT_ANSWERS)!!
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
        answers = ArrayList()
        editTexts = ArrayList()
        radioButtons = ArrayList()
        checkBoxes = ArrayList()

        setStartAnswers()
    }

    private fun setStartAnswers() {
        /*val radioGroup = RadioGroup(activity)
        val rgParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        radioGroup.layoutParams = rgParams*/

        liParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        liParams.setMargins(0, 8, 0, 8)

        tiParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tiParams.weight = 20f

        etParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


        rbParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rbParams.weight = 1f
        rbParams.topMargin = 16
        rbParams.bottomMargin = 4


        for (answer in question.answers) {
            addAnswer(answer)

        }
    }


    private fun setListeners() {
        btn_finish_questions!!.setOnClickListener(this)
        btn_next_question!!.setOnClickListener(this)
    }
    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_finish_questions -> {

                val rightAnswers: Int = checkAnswers()
                val args: Bundle = Bundle()
                args.putInt(RIGHT_ANSWERS,rightAnswers)
                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FinishFragment.newInstance(args))
                        .addToBackStack("AddQuestionFragment")
                        .commit()            }

            R.id.btn_next_question -> {
                val rightAnswers: Int = checkAnswers()
                val args: Bundle = Bundle()
                args.putInt(RIGHT_ANSWERS,rightAnswers)
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

    private fun checkAnswers(): Int {
        rights++
        for(i in question.answers.indices) {
            if(checkBoxes?.get(i)?.isChecked != answers?.get(i)?.isRight) {
                rights--
            }
        }
        return rights
    }

    private fun addAnswer(answer: Answer) {
        val liRadio = LinearLayout(activity)
        liRadio.orientation = LinearLayout.HORIZONTAL
        liRadio.layoutParams = liParams

        val textInputLayout = TextInputLayout(activity)
        textInputLayout.layoutParams = tiParams

        val textView = TextView(activity)
        textView.layoutParams = etParams
        textView.text = answer.text

        val radioButton = CheckBox(activity)
        radioButton.layoutParams = rbParams
        radioButton.isChecked = answer.isRight

        textInputLayout.addView(textView)
        liRadio.addView(textInputLayout)
        liRadio.addView(radioButton)

        checkBoxes?.add(radioButton)
        editTexts!!.add(textView)

        li_answers?.addView(liRadio)
    }

    companion object {

        private val RESULT_LOAD_IMG = 0

        const val QUESTION_NUMBER = "queston_number"

        const val RIGHT_ANSWERS = "right_answers"

        fun newInstance(args: Bundle): Fragment {
            val fragment = QuestionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
