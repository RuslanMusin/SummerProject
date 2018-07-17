package com.summer.itis.summerproject.ui.game.play

import com.arellomobile.mvp.MvpView
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.repository.json.GamesRepository

interface PlayGameView : MvpView {
    fun setEnemyUserData(user: User)

    fun setCardsList(cards: ArrayList<Card>)

    fun setCardChooseEnabled(enabled: Boolean)

    fun showEnemyCardChoose(card: Card)
    fun hideEnemyCardChoose()

    fun showQuestionForYou(question: Question)
    fun hideQuestionForYou()

    fun showYouCardChoose(choose: Card)//CardChooose? чтобы видеть вопрос для противника
    fun hideYouCardChoose()

    fun showEnemyAnswer(correct: Boolean)
    fun showYourAnswer(correct: Boolean)

    fun showGameEnd(type: GamesRepository.GameEndType, card: Card)
}
