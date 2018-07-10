package com.summer.itis.summerproject.ui.game

import com.arellomobile.mvp.MvpView

public interface FindGameView : MvpView {
    fun showNotSearching()
    fun showSearching()
}