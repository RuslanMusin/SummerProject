package com.summer.itis.summerproject.ui.game

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.GamesRepository

@InjectViewState
public class FindGamePresenter() : MvpPresenter<FindGameView>() {

    val gamesRepository: GamesRepository = RepositoryProvider.gamesRepository

    fun findGame() {
        viewState.showSearching()
    }

    fun cancelSearching() {
        viewState.showNotSearching()
    }
}