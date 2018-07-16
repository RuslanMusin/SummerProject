package com.summer.itis.summerproject.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.Expose

import java.util.ArrayList

@IgnoreExtraProperties
class Test {

    var id: String? = null

    var title: String? = null

    var desc: String? = null

    var authorId: String? = null

    var authorName: String? = null

    var cardId: String? = null

    var questions: MutableList<Question> = ArrayList()

    var type: String? = null

    var imageUrl: String? = null

    @Exclude
    var comments: MutableList<Comment> = ArrayList()

    @Exclude
    var card: Card? = null

    @Exclude
    var testDone: Boolean = false

    @Exclude
    lateinit var rightQuestions: List<Question>

    @Exclude
    lateinit var wrongQuestions: List<Question>
}
