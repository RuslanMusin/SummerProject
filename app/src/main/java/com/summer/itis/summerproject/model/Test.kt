package com.summer.itis.summerproject.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.summer.itis.summerproject.api.Card

import java.util.ArrayList

@IgnoreExtraProperties
class Test {

    var id: String? = null

    var title: String? = null

    var desc: String? = null

    var authorId: String? = null

    var authorName: String? = null

    @Exclude
    var questions: List<Question> = ArrayList()

    @Exclude
    var comments: List<Comment> = ArrayList()

    @Exclude
    var card: Card? = null
}
