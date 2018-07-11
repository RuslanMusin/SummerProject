package com.summer.itis.summerproject.repository.json

import android.graphics.Point
import android.util.Log
import com.google.firebase.database.*
import com.summer.itis.summerproject.R.string.comments
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import java.util.ArrayList
import java.util.HashMap

class AbstracCardRepository {

    var databaseReference: DatabaseReference? = null
        private set

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

    fun setDatabaseReference(path: String) {
        this.databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
    }

    fun getKey(crossingId: String): String? {
        return databaseReference!!.child(crossingId).push().key
    }

    fun findAbstractCard(wikiUrl: String) : String? {
        Log.d(TAG_LOG,"find abstr")
        val query: Query? = databaseReference?.orderByValue()?.equalTo(wikiUrl)
        var flag : Boolean = false;
        var id : String? = null
        query?.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot : DataSnapshot) {
                val comments = ArrayList<AbstractCard?>()
                for (postSnapshot in dataSnapshot.getChildren()) {
                    val point = postSnapshot.getValue(AbstractCard::class.java)
                    comments.add(point)
                    if(point?.wikiUrl.equals(wikiUrl)) {
                        id = point?.id
                    }
                    flag = true
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        while(!flag) {

        }
        return id

    }
}
