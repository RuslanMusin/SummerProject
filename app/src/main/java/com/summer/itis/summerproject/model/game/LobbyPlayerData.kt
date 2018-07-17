package com.summer.itis.summerproject.model.game

data class LobbyPlayerData(
        var playerId: String,
        var online: Boolean,
        var randomSendOnLoseCard: String?,
        var choosedCards: Map<String, CardChoose>?,
        var answers: Map<String, Boolean>?
) {
    companion object {
        val PARAM_playerId = "playerId"
        val PARAM_online = "online"
        val PARAM_randomSendOnLoseCard = "randomSendOnLoseCard"
        val PARAM_choosedCards = "choosedCards"
        val PARAM_answers = "answers"
    }
}
