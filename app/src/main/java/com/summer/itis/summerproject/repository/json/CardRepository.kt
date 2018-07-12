package com.summer.itis.summerproject.repository.json

import com.google.firebase.database.*
import com.summer.itis.summerproject.R.string.card
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.model.db_dop_models.ElementId
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.abstractCardRepository
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.testRepository
import com.summer.itis.summerproject.utils.Const
import com.summer.itis.summerproject.utils.Const.OFFICIAL_TYPE
import com.summer.itis.summerproject.utils.RxUtils
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class CardRepository {

    var databaseReference: DatabaseReference

    val TABLE_NAME = "test_cards"
    val USERS_CARDS = "users_cards"

    private val FIELD_ID = "id"
    private val FIELD_CARD_ID = "cardId"
    private val FIELD_TEST_ID = "testId"
    private val FIELD_INTELLIGENCE = "intelligence"
    private val FIELD_SUPPORT = "support"
    private val FIELD_PRESTIGE = "prestige"
    private val FIELD_HP = "hp"
    private val FIELD_STRENGTH = "strength"
    private val FIELD_TYPE = "type"


    init {
        this.databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
    }

    fun toMap(card: Card?): Map<String, Any?> {
        val result = HashMap<String, Any?>()

        val id = databaseReference!!.push().key
        card?.let {
            card.id = id
            result[FIELD_ID] = card.id
            result[FIELD_TEST_ID] = card.testId
            result[FIELD_CARD_ID] = card.cardId
            result[FIELD_INTELLIGENCE] = card.intelligence
            result[FIELD_PRESTIGE] = card.prestige
            result[FIELD_HP] = card.hp
            result[FIELD_SUPPORT] = card.support
            result[FIELD_STRENGTH] = card.strength
            result[FIELD_TYPE] = card.type
        }
        return result
    }

    fun toMapId(cardId: String?): Map<String, Any?> {
        val result = HashMap<String, Any?>()
        result[FIELD_ID] = cardId
        return result
    }


    fun setDatabaseReference(path: String) {
        this.databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
    }

    fun getKey(crossingId: String): String? {
        return databaseReference!!.child(crossingId).push().key
    }

    fun addCardAfterGame(cardId: String , winnerId: String, loserId: String): Single<Boolean> {
        val single : Single<Boolean> = Single.create { e ->
            val childUpdates = HashMap<String, Any>()
            val addCardValues = toMapId(cardId)
            childUpdates[USERS_CARDS + Const.SEP + winnerId] = addCardValues
            val removeCardValues = toMapId(null)
            childUpdates[USERS_CARDS + Const.SEP + loserId] = removeCardValues

            this.readCard(cardId)
                    .subscribe{card ->
                        card.cardId?.let {
                            this.findMyAbstractCardStates(it,winnerId)
                                    .subscribe{winnerCards ->
                                        if(winnerCards.size == 0) {
                                            val addAbstractCardValues = abstractCardRepository.toMapId(it)
                                            childUpdates[USERS_CARDS + Const.SEP + winnerId] = addAbstractCardValues
                                        }
                                        this.findMyAbstractCardStates(it,loserId)
                                            .subscribe{loserCards ->
                                                if(winnerCards.size == 1) {
                                                    val removeAbstractCardValues = abstractCardRepository.toMapId(null)
                                                    childUpdates[USERS_CARDS + Const.SEP + loserId] = removeAbstractCardValues
                                                }
                                                databaseReference.root.updateChildren(childUpdates)
                                                e.onSuccess(true)
                                            }
                                    }
                        }

                    }
        }
        return single.compose(RxUtils.asyncSingle())
    }

    fun readCard(cardId: String): Single<Card> {
        var card: Card?
        val query: Query = databaseReference.child(cardId)
        val single : Single<Card> = Single.create { e ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    card = dataSnapshot.getValue(Card::class.java)
                    AbstractCardRepository()
                            .findAbstractCard(card?.cardId)
                            .subscribe { t ->
                                card?.abstractCard = t
                                TestRepository()
                                    .readTest(card?.testId)
                                    .subscribe{ test ->
                                        card?.test = test
                                        e.onSuccess(card!!)
                                    }
                            }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        }
        return single.compose(RxUtils.asyncSingle())
    }

    fun findCards(cardsIds: List<String>): Single<List<Card>> {
        val single: Single<List<Card>> = Single.create{e ->
            Observable
                    .fromIterable(cardsIds)
                    .flatMap {
                        this.readCard(it).toObservable()
                    }
                    .toList()
                    .subscribe{cards ->
                        e.onSuccess(cards)
                    }
        }
        return single.compose(RxUtils.asyncSingle())
    }

    fun findOfficialMyCards(userId: String): Single<List<Card>> {
        val single:Single<List<Card>> =  Single.create { e ->
            findMyCards(userId).subscribe { cards ->
                val officials: MutableList<Card> = ArrayList()
                for (card in cards) {
                    if (card.type.equals(OFFICIAL_TYPE)) {
                        officials.add(card)
                    }
                }
                e.onSuccess(officials)
            }
        }
        return single.compose(RxUtils.asyncSingle())
    }

    fun findMyCards(userId: String): Single<List<Card>> {
        return Single.create { e ->
            val query: Query = databaseReference.root.child(USERS_CARDS).orderByValue().equalTo(userId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val elementIds: MutableList<String> = ArrayList()
                    for (snapshot in dataSnapshot.children) {
                        val elementId = snapshot.getValue(ElementId::class.java)
                        elementId?.let { elementIds.add(it.id) }
                    }
                    findCards(elementIds).subscribe{ cards ->
                        e.onSuccess(cards)
                    }

                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })
        }
    }

    fun findDefaultAbstractCardStates(abstractCardId: String): Single<List<Card>> {
        val query: Query = databaseReference.orderByChild(FIELD_CARD_ID).equalTo(abstractCardId)
        val single: Single<List<Card>> = Single.create { e ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val cards: MutableList<Card> = ArrayList()
                    for(snapshot in dataSnapshot.children) {
                        val card = snapshot.getValue(Card::class.java)
                        card?.let { cards.add(it) }

                    }
                    cards.let { e.onSuccess(it) }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        }
        return single.compose(RxUtils.asyncSingle())
    }



    fun findMyAbstractCardStates(abstractCardId: String, userId: String): Single<List<Card>> {
        val single: Single<List<Card>> = Single.create { e ->
            var query: Query = databaseReference.root.child(USERS_CARDS).orderByValue().equalTo(userId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val elementIds:MutableList<String> = ArrayList()
                    for(snapshot in dataSnapshot.children) {
                        val elementId = snapshot.getValue(ElementId::class.java)
                        elementId?.let { elementIds.add(it.id) }
                    }
                    query = databaseReference.orderByChild(FIELD_CARD_ID).equalTo(abstractCardId)
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val cards: MutableList<Card> = ArrayList()
                            for(snapshot in dataSnapshot.children) {
                                val card = snapshot.getValue(Card::class.java)
                                if(elementIds.contains(card?.id)) {
                                    card?.let { cards.add(it) }
                                }
                            }
                            e.onSuccess(cards)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }

                override fun onCancelled(p0: DatabaseError) {
                }

            })


        }
        return single.compose(RxUtils.asyncSingle())
    }

    fun findDefaultAbstractCardTests(abstractCardId: String): Single<List<Test>> {
        var query: Query = databaseReference.orderByChild(FIELD_CARD_ID).equalTo(abstractCardId)
        val single: Single<List<Test>> = Single.create { e ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val cards: MutableList<String> = ArrayList()
                    for(snapshot in dataSnapshot.children) {
                        val card = snapshot.getValue(Card::class.java)
                        card?.let { it.testId?.let { it1 -> cards.add(it1) } }
                    }
                    val list: Single<List<Test>> = Observable.fromIterable(cards).flatMap {
                        testRepository?.readTest(it)?.toObservable()
                    }.toList()
                    list.subscribe{tests ->
                        e.onSuccess(tests)
                    }
                }


                override fun onCancelled(databaseError: DatabaseError) {}
            })

        }
        return single.compose(RxUtils.asyncSingle())
    }

    fun findMyAbstractCardTests(abstractCardId: String, userId: String): Single<List<Test>> {
        val single: Single<List<Test>> = Single.create { e ->
            var query: Query = databaseReference.root.child(USERS_CARDS).orderByValue().equalTo(userId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val elementIds:MutableList<String> = ArrayList()
                    for(snapshot in dataSnapshot.children) {
                        val elementId = snapshot.getValue(ElementId::class.java)
                        elementId?.let { elementIds.add(it.id) }
                    }
                    query = databaseReference.orderByChild(FIELD_CARD_ID).equalTo(abstractCardId)
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val cards: MutableList<String> = ArrayList()
                                for(snapshot in dataSnapshot.children) {
                                    val card = snapshot.getValue(Card::class.java)
                                    card?.let { it.testId?.let { it1 -> cards.add(it1) } }
                                }
                                val list: Single<List<Test>> = Observable.fromIterable(cards).flatMap {
                                    testRepository?.readTest(it)?.toObservable()
                                }.toList()
                                list.subscribe{tests ->
                                    e.onSuccess(tests)
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {}
                        })

                    }
                override fun onCancelled(p0: DatabaseError) {
                }

            })


        }
        return single.compose(RxUtils.asyncSingle())
    }


    /*public void createPoint(BookCrossing crossing, Point point) {
        String pointKey = getKey(crossing.getId());
        Map<String, Object> pointValues = toMap(point);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(TABLE_NAME + SEP + crossing.getId() + SEP + pointKey, pointValues);
        databaseReference.getRoot().updateChildren(childUpdates);
    }

    public DatabaseReference readPoint(String pointId) {
        return databaseReference.child(pointId);
    }

    public void deletePoint(String pointId){
        databaseReference.child(pointId).removeValue();
    }

    public void updateUser(Point point){
        Map<String, Object> updatedValues = new HashMap<>();
        databaseReference.child(point.getId()).updateChildren(updatedValues);
    }

    public DatabaseReference getPoints() {
        return databaseReference.getRoot();
    }

    public Single<Query> loadPoints(String crossingId){
        return Single.just(databaseReference.child(crossingId));

    }

    public Single<Query> findPoint(String crossingId, String userId){
        DatabaseReference reference = databaseReference.child(crossingId);
        Query query = reference.orderByChild(FIELD_EDITOR).equalTo(userId);
        return Single.just(query);

    }*/
}
