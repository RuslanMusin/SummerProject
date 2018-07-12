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

package com.summer.itis.summerproject.utils

import android.util.Log
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.summer.itis.summerproject.Application
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.UserRepository
import com.summer.itis.summerproject.ui.start.login.LoginActivity

import com.summer.itis.summerproject.utils.Const.TAG_LOG

//ОСНОВНОЙ КЛАСС HELPER приложения. ОТСЮДА БЕРЕМ ТЕКУЩЕГО ЮЗЕРА ИЗ БД, ГРУЗИМ ФОТКУ ЮЗЕРА В ПРОФИЛЬ,
//ПОЛУЧАЕМ ССЫЛКУ НА ПУТЬ ФАЙЛОГО ХРАНИЛИЩА И СОЗДАЕМ СЕССИЮ. ПОКА ТАК ПУСТЬ БУДЕТ
class ApplicationHelper {

    companion object {

        var currentUser: User? = null

        val storageReference: StorageReference
            get() = FirebaseStorage.getInstance().reference

        fun loadUserPhoto(photoView: ImageView) {
            val storageReference = currentUser!!.photoUrl?.let { FirebaseStorage.getInstance().reference.child(it) }

            Glide.with(photoView.context)
                    .load(storageReference)
                    .into(photoView)
        }


        fun initUserState(application: Application) {
            val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                val user = firebaseAuth.currentUser
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Log.d(TAG_LOG, "logout")
                    LoginActivity.start(application)
                } else {
                    Log.d(TAG_LOG, "try to login")
                    val reference = RepositoryProvider.userRepository?.readUser(UserRepository.currentId)
                    reference?.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val user = dataSnapshot.getValue(User::class.java)
                            currentUser = user
                            LoginActivity.start(application.applicationContext)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                }
            }
        }

    }
}
