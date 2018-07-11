package com.summer.itis.summerproject.ui.game.find

import com.arellomobile.mvp.MvpView

interface FindGameView : MvpView {
    fun showNotSearching()
    fun showSearching()
    fun gameFinded()
}