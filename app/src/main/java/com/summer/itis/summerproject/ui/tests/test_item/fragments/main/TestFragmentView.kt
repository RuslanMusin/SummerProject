package com.summer.itis.summerproject.ui.tests.test_item.fragments.main

import com.arellomobile.mvp.MvpView
import com.summer.itis.summerproject.model.Comment

interface TestFragmentView : MvpView {

    fun showComments(comments: List<Comment>)

    fun addComment(comment: Comment)


}


