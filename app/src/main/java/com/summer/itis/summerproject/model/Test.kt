package com.summer.itis.summerproject.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

import java.util.ArrayList

@IgnoreExtraProperties
class Test {

    var id: String? = null

    var title: String? = null

    var desc: String? = null

    var authorId: String? = null

    var authorName: String? = null

    @Exclude
    var questions: MutableList<Question> = ArrayList()

    @Exclude
    var comments: MutableList<Comment> = ArrayList()

    @Exclude
    var card: Card? = null
}
