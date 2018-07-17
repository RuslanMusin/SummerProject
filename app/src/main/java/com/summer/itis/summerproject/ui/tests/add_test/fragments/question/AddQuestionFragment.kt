package com.summer.itis.summerproject.ui.tests.add_test.fragments.question

import GameQuestionFragment.Companion.QUESTION_NUMBER
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
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton

import com.jaredrummler.materialspinner.MaterialSpinner
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Answer
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView

import java.util.ArrayList

import android.app.Activity.RESULT_OK
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.testRepository
import com.summer.itis.summerproject.ui.base.BaseBackActivity
import com.summer.itis.summerproject.ui.base.OnFourActionListener
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity
import com.summer.itis.summerproject.ui.tests.ChangeToolbarListener
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity.Companion.ADD_QUESTION_FRAGMENT
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity.Companion.ADD_TEST_FRAGMENT
import com.summer.itis.summerproject.ui.tests.add_test.fragments.main.AddTestFragment
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.ANSWERS_FRAGMENT
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.TEST_JSON
import com.summer.itis.summerproject.ui.tests.test_item.fragments.check_answers.AnswersFragment
import com.summer.itis.summerproject.ui.tests.test_item.fragments.finish.FinishFragment
import com.summer.itis.summerproject.ui.tests.test_list.test.TestListActivity
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.Const.TEST_MANY_TYPE
import com.summer.itis.summerproject.utils.Const.TEST_ONE_TYPE
import com.summer.itis.summerproject.utils.Const.gsonConverter

class AddQuestionFragment : Fragment(), View.OnClickListener, OnFourActionListener {

    private var imageUri: Uri? = null

    lateinit var test: Test
    private lateinit var question: Question
    private var addTestView: AddTestView? = null
    private var number: Int = 0

    private var tiQuestion: TextInputLayout? = null
    private var liAnswers: LinearLayout? = null
    private var btnAddAnswer: Button? = null
    private var btnNextQuestion: Button? = null
    private var btnFinish: Button? = null
    private var etQuestion: EditText? = null
    private var spinner: MaterialSpinner? = null

    private var answers: MutableList<Answer>? = null
    private var answerSize: Int = 0

    private var editTexts: MutableList<EditText> = ArrayList()
    private var checkBoxes: MutableList<CheckBox> = ArrayList()
    private var radioButtons: MutableList<RadioButton> = ArrayList()

    private var testType: String = TEST_ONE_TYPE

    private lateinit var liParams: LinearLayout.LayoutParams
    private lateinit var tiParams: LinearLayout.LayoutParams
    private lateinit var etParams: LinearLayout.LayoutParams
    private lateinit var rbParams: LinearLayout.LayoutParams

    private lateinit var checkListener: View.OnClickListener

    override fun onBackPressed() {

        if(number != 0) {
            beforeQuestion()
        } else {
            val args: Bundle = Bundle()
           args.putString(TEST_JSON, gsonConverter.toJson(test))
           val fragment = AddTestFragment.newInstance(args)
           (activity as BaseBackActivity).changeFragment(fragment, ADD_TEST_FRAGMENT)
        }
    }

    override fun onCancel() {
        TestListActivity.start(activity as Activity)
    }

    override fun onForward() {
        nextQuestion()
    }

    override fun onOk() {
        finishQuestions()
    }

    private fun beforeQuestion() {
        val args: Bundle = Bundle()
        args.putString(TEST_JSON, gsonConverter.toJson(test))
        args.putInt(QUESTION_NUMBER, --number)
        val fragment = AnswersFragment.newInstance(args)
        (activity as BaseBackActivity).changeFragment(fragment, ADD_QUESTION_FRAGMENT + number)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_question, container, false)

        test = gsonConverter.fromJson(arguments?.getString(TEST_JSON),Test::class.java)
        if(test.questions.size > number) {
            question = test.questions[number]
        } else {
            question = Question()
            test.questions.add(question)
        }
        number = arguments?.getInt(QUESTION_NUMBER)!!
        addTestView = activity as AddTestView?

        (activity as BaseBackActivity).currentTag = ADD_QUESTION_FRAGMENT + number
        (activity as ChangeToolbarListener).changeToolbar(AddTestActivity.ADD_QUESTION_FRAGMENT,"Вопрос ${number+1}")
        if(number == 9) {
            (activity as ChangeToolbarListener).showOk(true)
        } else {
            (activity as ChangeToolbarListener).showOk(false)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        etQuestion = view.findViewById(R.id.et_question)
        tiQuestion = view.findViewById(R.id.ti_question)
        liAnswers = view.findViewById(R.id.li_answers)
        btnAddAnswer = view.findViewById(R.id.btn_add_answer)
        btnNextQuestion = view.findViewById(R.id.btn_next_question)
        btnFinish = view.findViewById(R.id.btn_finish_questions)
        spinner = view.findViewById(R.id.spinner)
        spinner!!.setItems(getString(R.string.test_type_one), getString(R.string.test_type_many))

        answers = ArrayList()
        editTexts = ArrayList()
        radioButtons = ArrayList()
        checkBoxes = ArrayList()

        setStartAnswers()
    }

    private fun setStartAnswers() {
        checkListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(testType.equals(TEST_ONE_TYPE)) {
                    changeToOneType(v as CheckBox)

                }
            }

        }


        for (i in 0..2) {
           addAnswer()

        }
    }


    private fun setListeners() {
        btnAddAnswer!!.setOnClickListener(this)
        btnNextQuestion!!.setOnClickListener(this)
        btnFinish!!.setOnClickListener(this)
        spinner?.setOnItemSelectedListener(object : MaterialSpinner.OnItemSelectedListener<Any> {
            override fun onItemSelected(view: MaterialSpinner?, position: Int, id: Long, item: Any?) {
                when (position) {
                    0 -> {
                        changeToOneType(null)
                    }

                    1 -> {
                        changeToManyType()
                    }
                }
            }

        })
    }

    private fun changeToManyType() {
        testType = TEST_MANY_TYPE

    }

    private fun changeToOneType(check: CheckBox?) {
        var count = 0
        for(checkBox in checkBoxes) {
            if(checkBox.isChecked && (check == null || check.id != checkBox.id)) {
                count++
                checkBox.isChecked = if(count > 1) false else true
            }
        }

    }

    private fun finishQuestions() {
        prepareQuestion()
//        addTestView!!.createTest()
        ApplicationHelper.currentUser?.let { testRepository
                .createTest(test, it)
                .subscribe{e -> TestActivity.start(activity as Activity,test)} }
    }

    private fun nextQuestion() {
        if(number <= 9) {
            val args: Bundle = Bundle()
            args.putString(TEST_JSON, gsonConverter.toJson(test))
            args.putInt(QUESTION_NUMBER, ++number)
            prepareQuestion()
            val fragment = AddQuestionFragment.newInstance(args)
            (activity as BaseBackActivity).changeFragment(fragment, ADD_QUESTION_FRAGMENT + number)
        }
    }

    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_finish_questions -> {
                finishQuestions()

            }

            R.id.btn_next_question -> {
                nextQuestion()
            }

            R.id.btn_add_answer -> {
                if(answerSize < 5) {
                    addAnswer()

                }

            }

        }
    }

   /* fun finishQuestion() {
        prepareQuestion()
        addTestView!!.createTest()
    }
*/
    private fun addAnswer() {
        answerSize++
        val view: View = layoutInflater.inflate(R.layout.layout_item_add_question,liAnswers,false)
        val editText: EditText = view.findViewById(R.id.et_answer)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)

        editTexts?.add(editText)
        checkBoxes?.add(checkBox)
        checkBox.setOnClickListener(checkListener)
      /*  editText.setOnFocusChangeListener(object: View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                Log.d(TAG_LOG,"has focus = $hasFocus and text = ${editText.text} and mode = ${editText.isInEditMode}")
                if(hasFocus || !(editText.text == null) || !(editText.text.toString().trim().equals("")) ) {
                    Log.d(TAG_LOG,"margin  = 0")
                    (checkBox.layoutParams as LinearLayout.LayoutParams).topMargin = 0
                } else {
                    Log.d(TAG_LOG,"margin  = 28")
                    (checkBox.layoutParams as LinearLayout.LayoutParams).topMargin = ApplicationHelper.convertDpToPx(28f,activity as Context)
                }
            }

        })*/
        /*editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG_LOG,"after = $s")
                if(s == null || "".equals(s.trim())) {
                    Log.d(TAG_LOG,"margin 28")
                    (checkBox.layoutParams as LinearLayout.LayoutParams).topMargin = ApplicationHelper.convertDpToPx(28f,activity as Context)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG_LOG,"before = $s")
                if(s == null || "".equals(s.trim())) {
                    Log.d(TAG_LOG,"margin 0")
                    (checkBox.layoutParams as LinearLayout.LayoutParams).topMargin = 0
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG_LOG,"changed = $s")

            }

        })*/

        liAnswers?.addView(view)
    }


    private fun prepareQuestion() {

        answers = ArrayList()
        for (i in checkBoxes!!.indices) {
            val answer = Answer()
            answer.text = editTexts!![i].text.toString()
            if (checkBoxes!![i].isChecked) {
                answer.isRight = true
            }
            answers!!.add(answer)
        }

        checkBoxes!!.clear()
        editTexts!!.clear()

        question!!.question = etQuestion!!.text.toString()
        //        question.setPhotoUrl(imageUri.getPath());
        question!!.answers = answers as ArrayList<Answer>
//        addTestView!!.setQuestion(question!!)

    }

    private fun addPhoto() {
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            imageUri = data!!.data
        }
    }

    companion object {

        private val RESULT_LOAD_IMG = 0

        fun newInstance(args: Bundle): Fragment {
            val fragment = AddQuestionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
