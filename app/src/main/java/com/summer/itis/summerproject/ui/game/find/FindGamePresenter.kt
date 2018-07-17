package com.summer.itis.summerproject.ui.game.find

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.GamesRepository
import com.summer.itis.summerproject.repository.json.UserRepository

@InjectViewState
class FindGamePresenter() : MvpPresenter<FindGameView>() {

    init {
        //TODO resetableLazy?
        RepositoryProvider.gamesRepository.resetData()

        viewState.showNothing()
        RepositoryProvider.cardRepository.findMyCards(UserRepository.currentId)
                .subscribe { t: List<Card>? ->
                    //                    Log.d("Alm","FindGamePresenter subscribe")
                    if (t!!.size >= GamesRepository.ROUNDS_COUNT) {
                        viewState.showNotSearching()
                    } else {
                        viewState.showNotEnoughCards()
                    }
                }
    }

    val gamesRepository = RepositoryProvider.gamesRepository

    fun findGame() {
        viewState.showSearching()
        gamesRepository.startSearchGame { viewState.gameFinded() }
    }

    fun cancelSearching() {
        gamesRepository.cancelSearchGame { viewState.showNotSearching() }
    }
}
