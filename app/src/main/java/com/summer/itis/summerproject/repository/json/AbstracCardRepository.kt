package com.summer.itis.summerproject.repository.json

import android.util.Log
import com.google.firebase.database.*
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.RxUtils
import io.reactivex.Single
import java.util.ArrayList
import java.util.HashMap

class AbstracCardRepository {

    private val databaseReference: DatabaseReference


    val TABLE_NAME = "abstract_cards"

    private val FIELD_ID = "id"
    private val FIELD_WIKI_URL = "wikiUrl"
    private val FIELD_NAME = "name"
    private val FIELD_PHOTO_URL = "photoUrl"
    private val FIELD_EXTRACT = "extract"
    private val FIELD_DESCRIPTION = "description"


    init {
        this.databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
    }

    fun toMap(card: AbstractCard?): Map<String, Any?> {
        val result = HashMap<String, Any?>()

        val id = databaseReference!!.push().key
        card?.let {
            card.id = id
            result[FIELD_ID] = card.id
            result[FIELD_NAME] = card.name
            result[FIELD_PHOTO_URL] = card.photoUrl
            result[FIELD_WIKI_URL] = card.wikiUrl
            result[FIELD_EXTRACT] = card.extract
            result[FIELD_DESCRIPTION] = card.description
        }
        return result
    }

    fun getKey(crossingId: String): String? {
        return databaseReference!!.child(crossingId).push().key
    }

    fun findAbstractCard(test: Test, user: User, listener: Listener) {
        val card = test.card
        val abstractCard = card?.abstractCard
        val wikiUrl = abstractCard?.wikiUrl;
        var id: String? = null

        Log.d(TAG_LOG,"find abstr = " + wikiUrl)

        val query: Query? = databaseReference?.orderByChild(FIELD_WIKI_URL)?.equalTo(wikiUrl)

        query?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val comments = ArrayList<AbstractCard?>()
                for (postSnapshot in dataSnapshot.getChildren()) {
                    val point = postSnapshot.getValue(AbstractCard::class.java)
                    comments.add(point)
                    if (point?.wikiUrl.equals(wikiUrl)) {
                        id = point?.id
                    }


                }
                abstractCard?.id = id
                test.card?.abstractCard = abstractCard
                Log.d(TAG_LOG, "set flag")
                listener.createTest(test,user,"create")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    fun findAbstractCard(cardId: String?): Single<AbstractCard> {
        var card: AbstractCard?
        val query: Query = databaseReference.child(cardId!!)
        val single: Single<AbstractCard> = Single.create { e ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    card = dataSnapshot.getValue(AbstractCard::class.java)
                    card?.let { e.onSuccess(it) }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        }
        return single.compose(RxUtils.asyncSingle())
    }
}
