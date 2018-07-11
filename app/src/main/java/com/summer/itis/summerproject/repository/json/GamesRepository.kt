package com.summer.itis.summerproject.repository.json

import com.google.firebase.database.*
import com.summer.itis.summerproject.model.game.Lobby

//Чет это уже не репозиторий, кажется
class GamesRepository {
    val allDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    val TABLE_GAMES = "games_v2"
    val gamesDbRef: DatabaseReference = allDbRef.child(TABLE_GAMES)

    val TABLE_SEARCHING = "searching"
    val searchingDbRef: DatabaseReference = gamesDbRef.child(TABLE_SEARCHING)

    val TABLE_LOBBIES = "lobbies"
    val lobbiesDbRef: DatabaseReference = gamesDbRef.child(TABLE_LOBBIES)

    var nowLobbyDbRef: DatabaseReference? = null

    var nowSearchingDbRef: DatabaseReference? = null

    //var onFind: (() -> Unit)? =null

    fun startSearchGame(onFind: () -> (Unit)) {
        searchingDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    val selected = dataSnapshot.children.first()

                    val lobbyId = selected.value as String

                    selected.ref.removeValue()

                    goToLobby(lobbyId)
                    onFind()
                } else {
                    createLobby(onFind)
                }

            }
        })
    }

    //onChildAdded

    private fun createLobby(onFind: () -> (Unit)) {
        var lobby = Lobby(getPlayerId(), "", true, false)

        nowLobbyDbRef = lobbiesDbRef.push()
        nowLobbyDbRef!!.setValue(lobby)

//        var nowSearchingDbRef = searchingDbRef.push()
        nowSearchingDbRef = searchingDbRef.child(nowLobbyDbRef!!.key!!)
        nowSearchingDbRef!!.setValue(nowLobbyDbRef!!.key)

        nowSearchingDbRef!!.onDisconnect().removeValue()

        nowLobbyDbRef!!.child(Lobby.PARAM_playerSecondId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.value != "") {
                    onFind()
                }
            }
        })

        nowLobbyDbRef!!.child(Lobby.PARAM_playerFirstOnline).onDisconnect().setValue(false)
    }

    private fun goToLobby(lobbyId: String) {
        nowLobbyDbRef = lobbiesDbRef.child(lobbyId)

        nowLobbyDbRef!!.child(Lobby.PARAM_playerSecondId).setValue(getPlayerId())
        nowLobbyDbRef!!.child(Lobby.PARAM_playerSecondOnline).setValue(true)

        nowLobbyDbRef!!.child(Lobby.PARAM_playerSecondOnline).onDisconnect().setValue(false)
    }

    //    fun cancelSearchGame(onCanceled: () -> (Unit)) {
    fun cancelSearchGame() {
        nowLobbyDbRef!!.child(Lobby.PARAM_playerFirstOnline).onDisconnect().cancel()

        nowSearchingDbRef!!.removeValue()
        nowLobbyDbRef!!.removeValue()

//        onCanceled()
    }

    fun getPlayerId(): String? {
        return UserRepository.getCurrentId()
    }
}
