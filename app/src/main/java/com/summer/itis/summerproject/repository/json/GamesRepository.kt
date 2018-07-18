package com.summer.itis.summerproject.repository.json

import android.util.Log
import com.google.firebase.database.*
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.game.CardChoose
import com.summer.itis.summerproject.model.game.Lobby
import com.summer.itis.summerproject.model.game.LobbyPlayerData
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.utils.getRandom


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

    var callbacks: InGameCallbacks? = null

    var lastEnemyChoose: CardChoose? = null
    var lastMyChosenCardId: String? = null

    var enemyId: String? = null

    var enemy_answers = 0;
    var enemy_score = 0;

    var my_answers = 0;
    var my_score = 0;

    var onYouLoseCard: Card? = null
    var onEnemyLoseCard: Card? = null

    var listeners = HashMap<DatabaseReference, ValueEventListener>()

    fun resetData() {
        nowLobbyDbRef = null
        nowSearchingDbRef = null
        currentPlayerLobbyDbRef = null
        enemyPlayerLobbyDbRef = null

        callbacks = null
        lastEnemyChoose = null
        lastMyChosenCardId = null
        enemyId = null
        enemy_answers = 0;
        enemy_score = 0;
        my_answers = 0;
        my_score = 0;

        onYouLoseCard = null
        onEnemyLoseCard = null

        removeListeners()


    }

    fun removeListeners() {
        for (l in listeners) {
            l.key.removeEventListener(l.value)
        }
        listeners.clear()
    }


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

                    goToLobby(lobbyId, onFind)

                } else {
                    createLobby(onFind)
                }

            }
        })
    }

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
                    enemyId = dataSnapshot.value as String
                    onFind()
                }
            }

        })

        currentPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_online).onDisconnect().setValue(false)
        //TODO remove lobby on disconnect?
    }

    private fun goToLobby(lobbyId: String, onFind: () -> (Unit)) {
        nowLobbyDbRef = lobbiesDbRef.child(lobbyId)

        enemyPlayerLobbyDbRef = nowLobbyDbRef!!.child(Lobby.PARAM_creator)
        currentPlayerLobbyDbRef = nowLobbyDbRef!!.child(Lobby.PARAM_invited)

        val lobbyPlayerData = LobbyPlayerData(getPlayerId()!!, true, null, null, null)
        currentPlayerLobbyDbRef!!.setValue(lobbyPlayerData)

        currentPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_online).onDisconnect().setValue(false)

        enemyPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_playerId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                enemyId = dataSnapshot.value as String

                onFind()
            }
        })
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


    //in game


    fun startGame(callbacks: InGameCallbacks) {
        this.callbacks = callbacks

        selectOnLoseCard()

        enemyPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_choosedCards)
                .addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                    override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {
//                        val cardChoose: CardChoose = dataSnapshot.getValue(CardChoose::class.java)!!
//                        callbacks.onEnemyCardChosen(cardChoose)
//                        lastEnemyChoose=cardChoose

                        lastEnemyChoose = dataSnapshot.getValue(CardChoose::class.java)!!
                        callbacks.onEnemyCardChosen(lastEnemyChoose!!)
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
                        Log.d("Alm", "onChildAdded to enemy answers")
                        val correct = dataSnapshot.value as Boolean
                        callbacks.onEnemyAnswered(correct)

                        enemy_answers++
                        if (correct) {
                            enemy_score++
                        }
                        checkGameEnd()
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {}
                })


        val connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected")
        val myConnectingLisener = connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java)!!
                if (!connected) {
                    onDisconnectAndLose()
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                System.err.println("Listener was cancelled")
            }
        })
        listeners.put(connectedRef, myConnectingLisener)


        val enemyConnectionListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.value == false) {
                    onEnemyDisconnectAndYouWin()
                }
            }
        }
        enemyPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_online)
                .addValueEventListener(enemyConnectionListener)
        listeners.put(enemyPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_online), enemyConnectionListener)
    }

    private fun selectOnLoseCard() {
        RepositoryProvider.cardRepository.findMyCards(enemyId!!).subscribe { enemyCards: List<Card>? ->
            RepositoryProvider.cardRepository.findMyCards(getPlayerId()!!).subscribe { myCards: List<Card>? ->
                onYouLoseCard = ArrayList(myCards).minus(enemyCards!!).getRandom()

                //TODO если нет подходящей карты
                // возможно стоит обрабатывать уже при входе в лобби

                currentPlayerLobbyDbRef!!
                        .child(LobbyPlayerData.PARAM_randomSendOnLoseCard)
                        .setValue(onYouLoseCard!!.id)
            }
        }

        enemyPlayerLobbyDbRef!!
                .child(LobbyPlayerData.PARAM_randomSendOnLoseCard)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            RepositoryProvider.cardRepository
                                    .readCard(dataSnapshot.value as String)
                                    .subscribe { t: Card? ->
                                        onEnemyLoseCard = t!!
                                    }
                        }
                    }
                })

    }

    fun chooseNextCard(cardId: String) {
        lastMyChosenCardId = cardId;
        RepositoryProvider.cardRepository.readCard(cardId).subscribe { card: Card? ->

            val questionId = card!!.test.questions.getRandom()!!.id


            val choose = CardChoose(cardId, questionId!!)

            currentPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_choosedCards).push().setValue(choose)
        }
    }

    fun answerOnLastQuestion(correct: Boolean) {
        val query: Query = enemyPlayerLobbyDbRef!!
                .child(LobbyPlayerData.PARAM_choosedCards)

                .orderByKey()
                .limitToLast(1)

        my_answers++
        if (correct) {
            my_score++
        }
        checkGameEnd()

        query.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val key = dataSnapshot.key!!

                currentPlayerLobbyDbRef!!.child(LobbyPlayerData.PARAM_answers)
                        .child(key)
                        .setValue(correct)

                query.removeEventListener(this)
            }

            override fun onChildRemoved(p0: DataSnapshot) {}
        })

    }

    private fun checkGameEnd() {
        if (enemy_answers == ROUNDS_COUNT && my_answers == ROUNDS_COUNT) {
            Log.d("Alm", "repo: GAME END!!!")

            Log.d("Alm", "repo: GAME END onEnemyLoseCard: " + onEnemyLoseCard!!.id)
            Log.d("Alm", "repo: GAME END onYouLoseCard: " + onYouLoseCard!!.id)

            //TODO

            if (my_score > enemy_score) {
                onWin()

            } else if (enemy_score > my_score) {
                onLose()

            } else {
                //TODO
                compareLastCards()
            }


        }
    }

    private fun compareLastCards() {
        RepositoryProvider.cardRepository
                .readCard(lastMyChosenCardId!!).subscribe { myLastCard: Card? ->
                    RepositoryProvider.cardRepository
                            .readCard(lastEnemyChoose!!.cardId).subscribe { enemyLastCard: Card? ->
                                var c = 0

                                c += compareCardsParameter({ card -> card.intelligence!! }, myLastCard!!, enemyLastCard!!)
                                c += compareCardsParameter({ card -> card.support!! }, myLastCard!!, enemyLastCard!!)
                                c += compareCardsParameter({ card -> card.prestige!! }, myLastCard!!, enemyLastCard!!)
                                c += compareCardsParameter({ card -> card.hp!! }, myLastCard!!, enemyLastCard!!)
                                c += compareCardsParameter({ card -> card.strength!! }, myLastCard!!, enemyLastCard!!)

                                if (c > 0) {
                                    onWin()
                                } else if (c < 0) {
                                    onLose()
                                } else {
                                    onDraw()
                                }

                            }
                }
    }

    private fun onDraw() {
        callbacks!!.onGameEnd(GameEndType.DRAW, onYouLoseCard!!)
    }

    fun compareCardsParameter(f: ((card: Card) -> Int), card1: Card, card2: Card): Int {
        return f(card1).compareTo(f(card2))
    }

    private fun onWin() {
        //TODO move card
        moveCardAfterWin()

        callbacks!!.onGameEnd(GameEndType.YOU_WIN, onEnemyLoseCard!!)

        removeListeners()

    }

    private fun onLose() {
        callbacks!!.onGameEnd(GameEndType.YOU_LOSE, onYouLoseCard!!)

        removeListeners()

    }

    private fun onDisconnectAndLose() {
        callbacks!!.onGameEnd(GameEndType.YOU_DISCONNECTED_AND_LOSE, onYouLoseCard!!)

        removeListeners()

    }

    private fun onEnemyDisconnectAndYouWin() {
        moveCardAfterWin()

        callbacks!!.onGameEnd(GameEndType.ENEMY_DISCONNECTED_AND_YOU_WIN, onEnemyLoseCard!!)

        removeListeners()

    }

    private fun moveCardAfterWin() {

        RepositoryProvider.cardRepository.addCardAfterGame(onEnemyLoseCard!!.id!!, getPlayerId()!!, enemyId!!)
                .subscribe { t: Boolean? ->

                }
    }


    //


    interface InGameCallbacks {
        fun onGameEnd(type: GameEndType, card: Card)

        fun onEnemyCardChosen(choose: CardChoose)
        fun onEnemyAnswered(correct: Boolean)
    }

    enum class GameEndType {
        YOU_WIN, YOU_LOSE, YOU_DISCONNECTED_AND_LOSE, ENEMY_DISCONNECTED_AND_YOU_WIN,
        DRAW
    }


    companion object {
        public val ROUNDS_COUNT = 2

    }
}
