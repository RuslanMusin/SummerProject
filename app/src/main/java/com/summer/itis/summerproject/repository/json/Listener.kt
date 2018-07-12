package com.summer.itis.summerproject.repository.json

import com.google.firebase.database.DataSnapshot
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.model.User

interface Listener {
    //this is for callbacks
    fun createTest(test: Test, user: User, type: String)

    /*fun onStart()

    fun onFailure()*/
}
