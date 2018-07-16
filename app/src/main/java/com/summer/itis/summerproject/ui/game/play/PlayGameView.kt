package com.summer.itis.summerproject.ui.game.play

import com.arellomobile.mvp.MvpView
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Question

interface PlayGameView : MvpView {
    fun setCardsList(cards: ArrayList<Card>)

//    fun showEnemyName

    fun setCardChooseEnabled(enabled: Boolean)

    fun showEnemyCardChoose(card: Card)
    fun showQuestionForYou(question: Question)

    fun showYouCardChoose(choose: Card)//CardChooose? чтобы видеть вопрос для противника

    fun showEnemyAnswer(correct: Boolean)

}
