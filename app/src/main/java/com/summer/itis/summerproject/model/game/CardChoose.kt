package com.summer.itis.summerproject.model.game

data class CardChoose(
        var cardId: String,
        var questionId: String
) {
    companion object {
        val PARAM_cardId = "cardId"
        val PARAM_questionId = "questionId"
    }
}
