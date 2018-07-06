package com.summer.itis.summerproject.ui.member.member_item;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.model.User;
import com.summer.itis.summerproject.model.repository.json.UserRepository;
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity;
import com.summer.itis.summerproject.ui.base.NavigationPresenter;
import com.summer.itis.summerproject.utils.ApplicationHelper;

import static com.summer.itis.summerproject.utils.Const.*;


public class PersonalActivity extends NavigationBaseActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView tvTests;
    private TextView tvCards;
    private TextView tvName;
    private AppCompatButton btnAddFriend;
    private ImageView ivPhoto;

    private User user;
    private String type;

    private PersonalPresenter presenter;

    public static void start(@NonNull Activity activity, @NonNull User comics) {
        Intent intent = new Intent(activity, PersonalActivity.class);
        Gson gson = new Gson();
        String userJson = gson.toJson(comics);
        intent.putExtra(USER_KEY,userJson);
        activity.startActivity(intent);
    }

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, PersonalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = findViewById(R.id.container);
        getLayoutInflater().inflate(R.layout.activity_profile, contentFrameLayout);

        presenter = new PersonalPresenter(this);

        String userJson = getIntent().getStringExtra(USER_KEY);
        presenter.setUserRelationAndView(userJson);

    }

    void initViews() {
        Log.d(TAG_LOG,"type = " + type);
        findViews();
        supportActionBar(toolbar);

        btnAddFriend.setOnClickListener(this);
        tvTests.setOnClickListener(this);
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        tvTests = findViewById(R.id.tv_crossings);
        ivPhoto = findViewById(R.id.iv_crossing);

        btnAddFriend = findViewById(R.id.btn_add_friend);
        tvName = findViewById(R.id.nameEditText);

        if (type.equals(OWNER_TYPE)) {
            user = NavigationPresenter.getCurrentUser();
            setUserData();
        } else {
            setUserData();
        }
    }

    private void setUserData() {
        tvName.setText(user.getUsername());

        String path = user.getPhotoUrl();
        if(!path.equals("path")) {
            StorageReference imageReference = ApplicationHelper.getStorageReference().child(user.getPhotoUrl());


            Log.d(TAG_LOG, "name " + imageReference.getPath());

            Glide.with(PersonalActivity.this)
                    .load(imageReference)
                    .into(ivPhoto);
        } else {
            Glide.with(PersonalActivity.this)
                    .load(R.drawable.ic_person_black_24dp)
                    .into(ivPhoto);
        }

        switch (type) {
            case ADD_FRIEND:
                btnAddFriend.setText(R.string.add_friend);
                break;

            case ADD_REQUEST:
                btnAddFriend.setText(R.string.add_friend);
                break;

            case REMOVE_FRIEND:
                btnAddFriend.setText(R.string.remove_friend);
                break;

            case REMOVE_REQUEST:
                btnAddFriend.setText(R.string.remove_request);
                break;

            case OWNER_TYPE :
                btnAddFriend.setVisibility(View.GONE);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.btn_change_data:
                changeData();
                break;*/

            case R.id.btn_add_friend :
                actWithUser();
                break;

            case R.id.tv_crossings :
                showCrossings();
        }
    }

    private void showCrossings() {
        User myUser = new User();
       /* if(type.equals(OWNER_TYPE)) {
            myUser.setId(UserRepository.getCurrentId());
            CrossingListActivity.start(this,myUser);
        } else {
            CrossingListActivity.start(this,user);
        }*/
    }

    private void actWithUser() {
        switch (type) {
            case ADD_FRIEND :
                new UserRepository().addFriend(UserRepository.getCurrentId(),user.getId());
                type = REMOVE_FRIEND;
                btnAddFriend.setText(R.string.remove_friend);
                break;

            case ADD_REQUEST :
                new UserRepository().addFriendRequest(UserRepository.getCurrentId(),user.getId());
                type = REMOVE_REQUEST;
                btnAddFriend.setText(R.string.remove_request);
                break;

            case REMOVE_FRIEND :
                new UserRepository().removeFriend(UserRepository.getCurrentId(),user.getId());
                type = ADD_FRIEND;
                btnAddFriend.setText(R.string.add_friend);
                break;

            case REMOVE_REQUEST :
                new UserRepository().removeFriendRequest(UserRepository.getCurrentId(),user.getId());
                type = ADD_REQUEST;
                btnAddFriend.setText(R.string.add_friend);
                break;

        }
    }

    private void changeData(){
//        startActivity(ChangeUserDataActivity.makeIntent(PersonalActivity.this));
    }

    //SET-GET


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
