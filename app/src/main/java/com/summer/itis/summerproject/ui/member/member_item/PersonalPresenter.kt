package com.summer.itis.summerproject.ui.member.member_item


import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.model.db_dop_models.Relation
import com.summer.itis.summerproject.repository.json.UserRepository

import com.summer.itis.summerproject.utils.Const.ADD_REQUEST
import com.summer.itis.summerproject.utils.Const.OWNER_TYPE
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.USER_KEY

class PersonalPresenter(private val testActivity: PersonalActivity) {

    fun setUserRelationAndView(userJson: String?) {
        if (userJson != null) {
            Log.d(TAG_LOG, "not my")
            val user = Gson().fromJson(testActivity.intent.getStringExtra(USER_KEY), User::class.java)
            testActivity.user = user
            if (user.id != UserRepository.currentId) {
                val query = user.id?.let { UserRepository().checkType(UserRepository.currentId, it) }
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val userRelation = dataSnapshot.getValue(Relation::class.java)
                            testActivity.type = userRelation!!.relation
                        } else {
                            testActivity.type = ADD_REQUEST
                        }
                        testActivity.initViews()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                }

                query?.addListenerForSingleValueEvent(listener)
            } else {
                testActivity.type = OWNER_TYPE
            }
        } else {
            testActivity.type = OWNER_TYPE
        }

        if (OWNER_TYPE == testActivity.type) {
            testActivity.initViews()
        }
    }
}
