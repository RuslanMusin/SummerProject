package com.summer.itis.summerproject.ui.tests.add_test.fragments.question

import QuestionFragment.Companion.QUESTION_NUMBER
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import android.widget.RadioGroup

import com.jaredrummler.materialspinner.MaterialSpinner
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Answer
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView

import java.util.ArrayList

import android.app.Activity.RESULT_OK
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.summer.itis.summerproject.Application
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.Const
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.TEST_MANY_TYPE
import com.summer.itis.summerproject.utils.Const.TEST_ONE_TYPE
import java.util.regex.Pattern

class AddQuestionFragment : Fragment(), View.OnClickListener {

    private var imageUri: Uri? = null

    private var question: Question? = null
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_question, container, false)

        question = Question()
        number = arguments?.getInt(QUESTION_NUMBER)!!
        addTestView = activity as AddTestView?

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

        checkListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(testType.equals(TEST_ONE_TYPE)) {
                    changeToOneType()

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
//                view?.let { Snackbar.make(it, "Clicked $item", Snackbar.LENGTH_LONG).show() }
                when (position) {
                    0 -> {
                        changeToOneType()
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

    private fun changeToOneType() {
        var count = 0
        for(checkBox in checkBoxes) {
            if(checkBox.isChecked) {
                count++
                checkBox.isChecked = if(count > 1) false else true
            }
        }

    }

    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_finish_questions -> {

                prepareQuestion()
                addTestView!!.createTest()
                PersonalActivity.start(activity!!)
            }

            R.id.btn_next_question -> {
                if(number <= 10) {
                    val args: Bundle = Bundle()
                    args.putInt(QUESTION_NUMBER, ++number)
                    prepareQuestion()
                    activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, AddQuestionFragment.newInstance(args))
                            .addToBackStack("AddQuestionFragment")
                            .commit()
                }
            }

            R.id.btn_add_answer -> {
                if(answerSize <= 5) {
                    addAnswer()

                }

            }

        }
    }

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
        addTestView!!.setQuestion(question!!)

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
