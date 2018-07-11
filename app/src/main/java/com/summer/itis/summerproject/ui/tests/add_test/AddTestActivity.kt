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
        setContentView(R.layout.activity_add_test)

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
        RepositoryProvider.testRepository!!.createTest(test!!, ApplicationHelper.currentUser!!)

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


    /* public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        MenuItem checkItem = menu.findItem(R.id.action_check);
        checkItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem items) {
                String title = etTitle.getText().toString();
                String desc = etDescription.getText().toString();
                String key = etPhrase.getText().toString();

                LatLng latLng = place.getLatLng();

                long date = 0;
                try {
                    date = sdf.parse(etDate.getText().toString()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                BookCrossing bookCrossing = new BookCrossing();
                bookCrossing.setBookId(book.getId());
                bookCrossing.setBookName(book.getName());
                bookCrossing.setBookPhoto(book.getPhotoUrl());
                bookCrossing.setBookAuthor(book.getAuthors().get(0));
                bookCrossing.setDate(date);
                bookCrossing.setKeyPhrase(key);
                bookCrossing.setDescription(desc);
                bookCrossing.setName(title);

                Point point = new Point();
                point.setDate(date);
                point.setDesc(desc);
                point.setLatitude(latLng.latitude);
                point.setLongitude(latLng.longitude);
                point.setPhotoUrl(place.getId());
                point.setEditorId(UserRepository.getCurrentId());

                List<Point> points = new ArrayList<>();
                points.add(point);

                bookCrossing.setPoints(points);

                RepositoryProvider.getBookCrossingRepository().createCrossing(bookCrossing, UserRepository.getCurrentId());

                FirebaseMessaging.getInstance().subscribeToTopic(bookCrossing.getId());

                CrossingActivity.start(AddTestActivity.this,bookCrossing);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/

}
