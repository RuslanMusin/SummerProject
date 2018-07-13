package com.summer.itis.summerproject.ui.game.play

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.GamesRepository

@InjectViewState
class PlayGamePresenter() : MvpPresenter<PlayGameView>(), GamesRepository.InGameCallbacks {

    val gamesRepository = RepositoryProvider.gamesRepository

    init {
        gamesRepository.startGame(this)
    }

    override fun onYouWin(cardId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEnemyWin(cardId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEnemyDisconnectedAndYouWin(cardId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onYouDisconnectedAndLose(cardId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEnemyCardChosen(cardId: String, questionId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEnemyAnswered(correct: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
