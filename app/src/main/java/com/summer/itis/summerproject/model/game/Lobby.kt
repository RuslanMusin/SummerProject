package com.summer.itis.summerproject.model.game

data class Lobby(var playerFirstId: String?,
                 var playerSecondId: String?,
                 var playerFirstOnline: Boolean?,
                 var playerSecondOnline: Boolean?
) {
    companion object {
        val PARAM_playerFirstId = "playerFirstId"
        val PARAM_playerSecondId = "playerSecondId"
        val PARAM_playerFirstOnline = "playerFirstOnline"
        val PARAM_playerSecondOnline = "playerSecondOnline"
    }
}