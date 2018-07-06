package com.summer.itis.summerproject.ui.member.member_item;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.summer.itis.summerproject.model.User;
import com.summer.itis.summerproject.model.db_dop_models.UserRelation;
import com.summer.itis.summerproject.model.repository.json.UserRepository;

import static com.summer.itis.summerproject.utils.Const.*;

public class PersonalPresenter {

    private PersonalActivity personalActivity;

    public PersonalPresenter(PersonalActivity personalActivity) {
        this.personalActivity = personalActivity;
    }

    public void setUserRelationAndView(String userJson) {
        if(userJson != null){
            Log.d(TAG_LOG,"not my");
            User user = new Gson().fromJson(personalActivity.getIntent().getStringExtra(USER_KEY),User.class);
            personalActivity.setUser(user);
            if(!user.getId().equals(UserRepository.getCurrentId())) {
                Query query = new UserRepository().checkType(UserRepository.getCurrentId(), user.getId());
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            UserRelation userRelation = dataSnapshot.getValue(UserRelation.class);
                            personalActivity.setType(userRelation.getRelation());
                        } else {
                            personalActivity.setType(ADD_REQUEST);
                        }
                        personalActivity.initViews();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };

                query.addListenerForSingleValueEvent(listener);
            } else {
                personalActivity.setType(OWNER_TYPE);
            }
        } else {
            personalActivity.setType(OWNER_TYPE);
        }

        if(OWNER_TYPE.equals(personalActivity.getType())) {
            personalActivity.initViews();
        }
    }
}
