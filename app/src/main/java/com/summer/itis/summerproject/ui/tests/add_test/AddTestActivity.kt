package com.summer.itis.summerproject.ui.tests.add_test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar

import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity
import com.summer.itis.summerproject.ui.tests.add_test.fragments.main.AddTestFragment
import com.summer.itis.summerproject.utils.ApplicationHelper

class AddTestActivity : NavigationBaseActivity(), AddTestView {

    internal var PLACE_PICKER_REQUEST = 1

    private var toolbar: Toolbar? = null

    @InjectPresenter
    lateinit var presenter: AddTestPresenter

    private var test: Test? = null

    private val containerId: Int
        get() = R.id.fragment_container

    protected val fragment: Fragment
        get() = AddTestFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_frame_and_toolbar)

        val fragmentManager = supportFragmentManager
        if (fragmentManager.findFragmentById(containerId) == null) {
            fragmentManager.beginTransaction()
                    .add(containerId, fragment)
                    .commit()
        }
        initViews()
    }

    fun getTest(): Test? {
        return test
    }

    override fun setQuestion(question: Question) {
        test!!.questions.add(question)

    }

    override fun createTest() {
        RepositoryProvider.testRepository!!.createTest(test!!, ApplicationHelper.currentUser!!,"read")

    }

    override fun setTest(test: Test) {
        this.test = test
    }

    private fun initViews() {
        findViews()
        //        supportActionBar(toolbar);
        setSupportActionBar(toolbar)
        setBackArrow(toolbar!!)

    }

    private fun findViews() {
        toolbar = findViewById(R.id.toolbar)


    }

    companion object {

        fun start(activity: Activity) {
            val intent = Intent(activity, AddTestActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
