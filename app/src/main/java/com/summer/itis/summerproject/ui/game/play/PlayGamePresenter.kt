package com.summer.itis.summerproject.ui.game.play

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.model.game.CardChoose
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.GamesRepository
import com.summer.itis.summerproject.repository.json.UserRepository

@InjectViewState
class PlayGamePresenter() : MvpPresenter<PlayGameView>(), GamesRepository.InGameCallbacks {


    val gamesRepository = RepositoryProvider.gamesRepository
    val cardsRepository = RepositoryProvider.cardRepository

    init {
        RepositoryProvider.userRepository.readUserById(gamesRepository.enemyId!!)
                .subscribe { t: User? ->
                    viewState.setEnemyUserData(t!!)
                }

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
        youCardChosed = true
        if (enemyCardChosed) {
            showQuestion()
        }
    }

    fun answer(correct: Boolean) {
        viewState.hideQuestionForYou()

        viewState.hideEnemyCardChoose()
        viewState.hideYouCardChoose()

        viewState.showYourAnswer(correct)

        gamesRepository.answerOnLastQuestion(correct)
        enemyCardChosed = false
        youCardChosed = false

    }

    override fun onGameEnd(type: GamesRepository.GameEndType, card: Card) {
        Log.d("Alm", "Game End: " + type)

        viewState.showGameEnd(type,card)

//        when(type){
//            GamesRepository.GameEndType.YOU_WIN->{
//                viewState.
//            }
//        }

    }

    var youCardChosed = false
    var enemyCardChosed = false

    var lastEnemyChoose: CardChoose? = null

    override fun onEnemyCardChosen(choose: CardChoose) {
        Log.d("Alm", "enemy chosen card " + choose.cardId)
        Log.d("Alm", "enemy chosen question " + choose.questionId)
        enemyCardChosed = true
        lastEnemyChoose = choose
        RepositoryProvider.cardRepository.readCard(choose.cardId).subscribe { card ->
            viewState.showEnemyCardChoose(card)
            if (youCardChosed) {
                showQuestion()
            }
        }
//        viewState.setCardChooseEnabled(true)
    }

    private fun showQuestion() {
        RepositoryProvider.cardRepository.readCard(lastEnemyChoose!!.cardId).subscribe { card ->
            viewState.showQuestionForYou(card.test.questions
                    .first { q -> q.id == lastEnemyChoose!!.questionId })
        }
    }

    override fun onEnemyAnswered(correct: Boolean) {
        viewState.showEnemyAnswer(correct)
        viewState.setCardChooseEnabled(true)
    }
}
