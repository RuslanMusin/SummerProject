package com.summer.itis.summerproject.ui.tests.add_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.api.Card;
import com.summer.itis.summerproject.model.Question;
import com.summer.itis.summerproject.model.Test;
import com.summer.itis.summerproject.repository.RepositoryProvider;
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity;
import com.summer.itis.summerproject.ui.tests.add_test.fragments.main.AddTestFragment;
import com.summer.itis.summerproject.utils.ApplicationHelper;

public class AddTestActivity extends NavigationBaseActivity implements AddTestView {

    int PLACE_PICKER_REQUEST = 1;

    private Toolbar toolbar;

    @InjectPresenter
    AddTestPresenter presenter;

    private Test test;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AddTestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentById(getContainerId()) == null){
            fragmentManager.beginTransaction()
                    .add(getContainerId(),getFragment())
                    .commit();
        }
        initViews();
    }

    private int getContainerId(){
        return R.id.fragment_container;
    }

    protected Fragment getFragment() {

        return AddTestFragment.newInstance();

    }

    public Test getTest() {
        return test;
    }

    @Override
    public void setQuestion(Question question) {
        test.getQuestions().add(question);

    }

    @Override
    public void setCard(Card card) {

    }

    @Override
    public void createTest() {
        RepositoryProvider.Companion.getTestRepository().createTest(test, ApplicationHelper.Companion.getCurrentUser());

    }

    public void setTest(Test test) {
        this.test = test;
    }

    private void initViews() {
        findViews();
//        supportActionBar(toolbar);
        setSupportActionBar(toolbar);
        setBackArrow(toolbar);

    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);


    }


   /* public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        MenuItem checkItem = menu.findItem(R.id.action_check);
        checkItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
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
