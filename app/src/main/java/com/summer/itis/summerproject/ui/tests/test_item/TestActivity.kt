package com.summer.itis.summerproject.ui.tests.test_item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter

import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity
import com.summer.itis.summerproject.ui.tests.add_test.AddTestPresenter
import com.summer.itis.summerproject.ui.tests.add_test.fragments.main.AddTestFragment
import com.summer.itis.summerproject.ui.tests.test_item.fragments.main.TestFragment
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.Const.gsonConverter


class TestActivity : NavigationBaseActivity(), TestView {

    internal var PLACE_PICKER_REQUEST = 1

    private var toolbar: Toolbar? = null

    @InjectPresenter
    lateinit var presenter: TestPresenter

    lateinit var test: Test

    private val containerId: Int
        get() = R.id.fragment_container

    protected val fragment: Fragment
        get() = AddTestFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_frame_and_toolbar)

        val testStr: String = intent.getStringExtra(TEST_JSON)
        val args: Bundle = Bundle()
        args.putString(TEST_JSON,testStr)

        val fragmentManager = supportFragmentManager
        if (fragmentManager.findFragmentById(containerId) == null) {
            fragmentManager.beginTransaction()
                    .add(containerId, TestFragment.newInstance(args))
                    .commit()
        }
        initViews()
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

        const val TEST_JSON: String = "test_json"

        fun start(activity: Activity, test: Test) {
            val intent = Intent(activity, AddTestActivity::class.java)
            val testStr: String = gsonConverter.toJson(test)
            intent.putExtra(TEST_JSON,testStr)
            activity.startActivity(intent)
        }
    }

}
