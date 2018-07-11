package com.summer.itis.summerproject.repository.json

import com.google.firebase.database.*
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.utils.RxUtils
import io.reactivex.Single

import java.util.HashMap

class CardRepository {

    var databaseReference: DatabaseReference

    val TABLE_NAME = "test_cards"

    private val FIELD_ID = "id"
    private val FIELD_CARD_ID = "cardId"
    private val FIELD_TEST_ID = "testId"
    private val FIELD_INTELLIGENCE = "intelligence"
    private val FIELD_SUPPORT = "support"
    private val FIELD_PRESTIGE = "prestige"
    private val FIELD_HP = "hp"
    private val FIELD_STRENGTH = "strength"

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
        }
        return result
    }

    fun setDatabaseReference(path: String) {
        this.databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
    }

    fun getKey(crossingId: String): String? {
        return databaseReference!!.child(crossingId).push().key
    }

    fun readCard(cardId: String): Single<Card> {
        var card: Card?
        val query: Query = databaseReference.child(cardId)
        val single : Single<Card> = Single.create { e ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    card = dataSnapshot.getValue(Card::class.java)
                    AbstracCardRepository()
                            .findAbstractCard(card?.cardId)
                            .subscribe { t ->
                                card?.abstractCard = t
                                TestRepository()
                                    .readTest(card?.testId)
                                    .subscribe{ test ->
                                        card?.test = test
                                        card?.let { e.onSuccess(it) }
                                    }
                            }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
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
