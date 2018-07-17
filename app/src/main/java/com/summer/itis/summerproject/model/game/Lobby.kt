package com.summer.itis.summerproject.model.game

data class Lobby(
        var creator: LobbyPlayerData,
        var invited: LobbyPlayerData?
) {
    companion object {
        val PARAM_creator = "creator"
        val PARAM_invited = "invited"
    }
}
