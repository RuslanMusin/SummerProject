package com.summer.itis.summerproject.ui.game.play

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.game.CardChoose
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.GamesRepository
import com.summer.itis.summerproject.repository.json.UserRepository

@InjectViewState
class PlayGamePresenter() : MvpPresenter<PlayGameView>(), GamesRepository.InGameCallbacks {

    val gamesRepository = RepositoryProvider.gamesRepository
    val cardsRepository = RepositoryProvider.cardRepository

    init {
        gamesRepository.startGame(this)

        cardsRepository.findMyCards(UserRepository.currentId).subscribe { t: List<Card>? ->
            viewState.setCardsList(ArrayList(t))

            viewState.setCardChooseEnabled(true)

        }
    }

    fun chooseCard(card: Card) {
        viewState.setCardChooseEnabled(false)
        gamesRepository.chooseNextCard(card.id!!)
        viewState.showYouCardChoose(card)
    }

    fun answer(correct: Boolean) {
        gamesRepository.answerOnLastQuestion(correct)
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

    override fun onEnemyCardChosen(choose: CardChoose) {
        RepositoryProvider.cardRepository.readCard(choose.cardId).subscribe { card ->
            viewState.showEnemyCardChoose(card)

        }
        viewState.setCardChooseEnabled(true)
    }

    override fun onEnemyAnswered(correct: Boolean) {
        viewState.showEnemyAnswer(correct)
    }
}
