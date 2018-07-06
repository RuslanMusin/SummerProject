/*
 * Copyright 2017 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.summer.itis.summerproject.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.summer.itis.summerproject.Application;
import com.summer.itis.summerproject.model.User;
import com.summer.itis.summerproject.model.repository.RepositoryProvider;
import com.summer.itis.summerproject.model.repository.json.UserRepository;
import com.summer.itis.summerproject.ui.base.NavigationPresenter;
import com.summer.itis.summerproject.ui.start.login.LoginActivity;

import static com.summer.itis.summerproject.utils.Const.TAG_LOG;


public class ApplicationHelper {

    public static StorageReference getStorageReference(){
        return FirebaseStorage.getInstance().getReference();
    }


    public static void initUserState(Application application) {
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Log.d(TAG_LOG,"logout");
                    LoginActivity.start(application);
                } else {
                    Log.d(TAG_LOG,"try to login");
                    DatabaseReference reference = RepositoryProvider.getUserRepository().readUser(UserRepository.getCurrentId());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            NavigationPresenter.setCurrentUser(user);
                            LoginActivity.start(application.getApplicationContext());
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }
}
