package com.summer.itis.summerproject.ui.member.member_item


import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.model.db_dop_models.UserRelation
import com.summer.itis.summerproject.repository.json.UserRepository

import com.summer.itis.summerproject.utils.Const.ADD_REQUEST
import com.summer.itis.summerproject.utils.Const.OWNER_TYPE
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.USER_KEY

class PersonalPresenter(private val personalActivity: PersonalActivity) {

    fun setUserRelationAndView(userJson: String?) {
        if (userJson != null) {
            Log.d(TAG_LOG, "not my")
            val user = Gson().fromJson(personalActivity.intent.getStringExtra(USER_KEY), User::class.java)
            personalActivity.user = user
            if (user.id != UserRepository.getCurrentId()) {
                val query = UserRepository().checkType(UserRepository.getCurrentId(), user.id)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val userRelation = dataSnapshot.getValue(UserRelation::class.java)
                            personalActivity.type = userRelation!!.relation
                        } else {
                            personalActivity.type = ADD_REQUEST
                        }
                        personalActivity.initViews()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                }

                query.addListenerForSingleValueEvent(listener)
            } else {
                personalActivity.type = OWNER_TYPE
            }
        } else {
            personalActivity.type = OWNER_TYPE
        }

        if (OWNER_TYPE == personalActivity.type) {
            personalActivity.initViews()
        }
    }
}
