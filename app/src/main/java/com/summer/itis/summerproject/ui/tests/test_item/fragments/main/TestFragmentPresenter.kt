package com.summer.itis.summerproject.ui.tests.test_item.fragments.main

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.model.Comment
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.testCommentRepository
import com.summer.itis.summerproject.utils.Const

@InjectViewState
class TestFragmentPresenter : MvpPresenter<TestFragmentView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d(Const.TAG_LOG, "attach listPresenter")
    }


    @SuppressLint("CheckResult")
    fun loadComments(crossingId: String) {
        testCommentRepository.getComments(crossingId)
                .subscribe{ comments ->
                    viewState?.showComments(comments)
                }
    }

    fun createComment(crossingId: String, comment: Comment) {
        testCommentRepository.createComment(crossingId,comment)
                .subscribe{e -> viewState.addComment(comment)}
    }

}