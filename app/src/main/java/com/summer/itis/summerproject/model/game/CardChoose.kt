package com.summer.itis.summerproject.model.game

class CardChoose {

    lateinit var cardId: String
    lateinit var questionId: String

    constructor() {

    }

    constructor(cardId: String, questionId: String) {
        this.cardId = cardId
        this.questionId = questionId
    }

    companion object {
        val PARAM_cardId = "cardId"
        val PARAM_questionId = "questionId"
    }
}
