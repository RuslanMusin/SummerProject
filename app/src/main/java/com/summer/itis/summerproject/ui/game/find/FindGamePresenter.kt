package com.summer.itis.summerproject.ui.game.find

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.repository.RepositoryProvider

@InjectViewState
class FindGamePresenter() : MvpPresenter<FindGameView>() {

    val gamesRepository = RepositoryProvider.gamesRepository

    fun findGame() {
        viewState.showSearching()
        gamesRepository.startSearchGame { viewState.gameFinded() }
    }

    fun cancelSearching() {
        gamesRepository.cancelSearchGame { viewState.showNotSearching() }
    }
}
