package com.summer.itis.summerproject.repository.json

import com.google.firebase.database.*
import com.summer.itis.summerproject.model.game.CardChoose
import com.summer.itis.summerproject.model.game.Lobby
import com.summer.itis.summerproject.model.game.LobbyPlayerData

class GamesRepository {
    val allDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    val TABLE_GAMES = "games_v3"
    val gamesDbRef: DatabaseReference = allDbRef.child(TABLE_GAMES)

    val TABLE_SEARCHING = "searching"
    val searchingDbRef: DatabaseReference = gamesDbRef.child(TABLE_SEARCHING)

    val TABLE_LOBBIES = "lobbies"
    val lobbiesDbRef: DatabaseReference = gamesDbRef.child(TABLE_LOBBIES)

    var nowLobbyDbRef: DatabaseReference? = null

    var nowSearchingDbRef: DatabaseReference? = null

    var currentPlayerLobbyDbRef: DatabaseReference? = null
    var enemyPlayerLobbyDbRef: DatabaseReference? = null

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
        val lobbyPlayerData = LobbyPlayerData(getPlayerId()!!, true, null, null, null)
        val lobby = Lobby(lobbyPlayerData, null)

        nowLobbyDbRef = lobbiesDbRef.push()
        nowLobbyDbRef!!.setValue(lobby)

        nowSearchingDbRef = searchingDbRef.child(nowLobbyDbRef!!.key!!)
        nowSearchingDbRef!!.setValue(nowLobbyDbRef!!.key)

        nowSearchingDbRef!!.onDisconnect().removeValue()

        currentPlayerLobbyDbRef = nowLobbyDbRef!!.child(Lobby.PARAM_creator)
        enemyPlayerLobbyDbRef = nowLobbyDbRef!!.child(Lobby.PARAM_invited)

        enemyPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_playerId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.value != "") {
                    onFind()
                }
            }

        })

        currentPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_online).onDisconnect().setValue(false)
        //TODO remove lobby on disconnect?
    }

    private fun goToLobby(lobbyId: String) {
        nowLobbyDbRef = lobbiesDbRef.child(lobbyId)

        enemyPlayerLobbyDbRef = nowLobbyDbRef!!.child(Lobby.PARAM_creator)
        currentPlayerLobbyDbRef = nowLobbyDbRef!!.child(Lobby.PARAM_invited)

        val lobbyPlayerData = LobbyPlayerData(getPlayerId()!!, true, null, null, null)
        currentPlayerLobbyDbRef!!.setValue(lobbyPlayerData)

        currentPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_online).onDisconnect().setValue(false)
    }

    fun cancelSearchGame(onCanceled: () -> (Unit)) {
        currentPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_online).onDisconnect().cancel()
        //TODO for parent ?

        nowSearchingDbRef!!.removeValue().addOnSuccessListener { onCanceled() }
        nowLobbyDbRef!!.removeValue()
    }

    fun getPlayerId(): String? {
        return UserRepository.currentId
    }

    var callbacks: InGameCallbacks? = null

    interface InGameCallbacks {
        fun onYouWin(cardId: String)
        fun onEnemyWin(cardId: String)
        fun onEnemyDisconnectedAndYouWin(cardId: String)
        fun onYouDisconnectedAndLose(cardId: String)//лучше Card,
        // т.к. без соединения не узнать название по Id ?
        fun onEnemyCardChosen(choose: CardChoose)

        fun onEnemyAnswered(correct: Boolean)
    }

    fun startGame(callbacks: InGameCallbacks) {

        this.callbacks = callbacks

        //TODO
        //select onLoseCard

        enemyPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_choosedCards)
                .addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                    override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {
                        val cardChoose: CardChoose = dataSnapshot.getValue(CardChoose::class.java)!!
                        callbacks.onEnemyCardChosen(cardChoose)
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {}
                })

        enemyPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_answers)
                .addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                    override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {
                        callbacks.onEnemyAnswered(dataSnapshot.value as Boolean)
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {}
                })

    }

    fun chooseNextCard(id: String) {
        TODO()
    }

    fun answerOnLastQuestion(correct: Boolean) {
        TODO()
    }

}
