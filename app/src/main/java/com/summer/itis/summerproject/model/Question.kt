package com.summer.itis.summerproject.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Question {

    var id: String? = null

    var question: String? = null

    var answers: List<Answer>? = null
}
