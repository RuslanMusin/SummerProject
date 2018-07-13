package com.summer.itis.summerproject.repository.json

import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.summer.itis.summerproject.model.Comment
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.model.db_dop_models.ElementId
import com.summer.itis.summerproject.model.db_dop_models.Relation

import java.util.ArrayList
import java.util.HashMap
import java.util.Objects

import io.reactivex.Single

import com.summer.itis.summerproject.utils.Const.ADD_FRIEND
import com.summer.itis.summerproject.utils.Const.QUERY_END
import com.summer.itis.summerproject.utils.Const.REMOVE_FRIEND
import com.summer.itis.summerproject.utils.Const.REMOVE_REQUEST
import com.summer.itis.summerproject.utils.Const.SEP

class UserRepository {
    private val databaseReference: DatabaseReference

    private val TABLE_NAME = "users"
    private val CROSSING_MEMBERS = "crossing_members"
    private val USER_FRIENDS = "user_friends"
    private val USER_REQUESTS = "user_requests"


    private val FIELD_ID = "id"
    private val FIELD_NAME = "username"
    private val FIELD_RELATION = "relation"

    init {
        this.databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
    }

    fun createUser(user: User) {
        databaseReference.child(user.id!!).setValue(user) { databaseError, databaseReference ->
            if (databaseError != null) {
                Log.d(TAG, "database error = " + databaseError.message)
            }
            Log.d(TAG, "completed ")
        }
    }

    fun readUser(userId: String): DatabaseReference {
        return databaseReference.child(userId)
    }

    fun deleteUser(user: User) {
        databaseReference.child(user.id!!).removeValue()
    }

    fun updateUser(user: User) {
        val updatedValues = HashMap<String, Any>()
        databaseReference.child(user.id!!).updateChildren(updatedValues)
    }

    fun loadDefaultUsers(): Single<Query> {
        return Single.just(databaseReference.limitToFirst(100))
    }

    fun loadByCrossing(crossingId: String): Single<Query> {
        val reference = databaseReference.root.child(CROSSING_MEMBERS).child(crossingId)
        return Single.just(reference)
    }

    fun loadReadersByQuery(query: String): Single<Query> {
        val queryPart = query.trim { it <= ' ' }
        val queryName = databaseReference.orderByChild(FIELD_NAME).startAt(queryPart).endAt(queryPart + QUERY_END).limitToFirst(100)
        return Single.just(queryName)
    }

    fun loadFriendsByQuery(query: String, userId: String): Single<List<Query>> {
        val queryPart = query.trim { it <= ' ' }
        val reference = databaseReference.root.child(USER_FRIENDS).child(userId)
        return findByReference(reference, queryPart)
    }

    fun loadRequestByQuery(query: String, userId: String): Single<List<Query>> {
        val queryPart = query.trim { it <= ' ' }
        val reference = databaseReference.root.child(USER_FRIENDS).child(userId)
        return findByReference(reference, queryPart)
    }

    private fun findByReference(reference: DatabaseReference, queryPart: String): Single<List<Query>> {
        val queries = ArrayList<Query>()
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(ElementId::class.java)
                    var queryDb: Query? = databaseReference.orderByChild(FIELD_ID).equalTo(user!!.id)
                    queryDb = queryDb!!
                            .orderByChild(FIELD_NAME)
                            .startAt(queryPart)
                            .endAt(queryPart + QUERY_END)
                    if (queryDb != null) {
                        queries.add(queryDb)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        return Single.just(queries)
    }

    fun loadByIds(crossingsIds: List<String>): Single<List<Query>> {
        val queries = ArrayList<Query>()
        for (id in crossingsIds) {
            queries.add(databaseReference.child(id))
        }
        return Single.just(queries)
    }

    fun loadByComments(comments: List<Comment>): List<Query> {
        val queries = ArrayList<Query>()
        for (comment in comments) {
            queries.add(databaseReference.child(comment.authorId!!))
        }
        return queries
    }

    fun findFriends(userId: String): Single<Query> {
        val reference = databaseReference.root.child(USER_FRIENDS).child(userId).orderByChild(FIELD_RELATION)
                .equalTo(REMOVE_FRIEND)
        return Single.just(reference)
    }

    fun findRequests(userId: String): Single<Query> {
        val reference = databaseReference.root.child(USER_FRIENDS).child(userId).orderByChild(FIELD_RELATION)
                .equalTo(REMOVE_REQUEST)
        return Single.just(reference)
    }

    fun addFriend(userId: String, friendId: String) {
        val userValues = Relation.toMap(userId, REMOVE_FRIEND)
        val friendValues = Relation.toMap(friendId, REMOVE_FRIEND)
        val childUpdates = HashMap<String, Any>()
        childUpdates[USER_FRIENDS + SEP + userId + SEP + friendId] = friendValues
        childUpdates[USER_FRIENDS + SEP + friendId + SEP + userId] = userValues

        databaseReference.root.updateChildren(childUpdates)
    }

    fun removeFriend(userId: String, friendId: String) {
        val userValues = Relation.toMap(userId, REMOVE_REQUEST)
        val childUpdates = HashMap<String, Any?>()
        childUpdates[USER_FRIENDS + SEP + userId + SEP + friendId] = null
        childUpdates[USER_FRIENDS + SEP + friendId + SEP + userId] = userValues

        databaseReference.root.updateChildren(childUpdates)
    }

    fun addFriendRequest(userId: String, friendId: String) {
        val friendValues = Relation.toMap(friendId, REMOVE_FRIEND)
        val userValues = Relation.toMap(userId, ADD_FRIEND)
        val childUpdates = HashMap<String, Any>()
        childUpdates[USER_FRIENDS + SEP + userId + SEP + friendId] = friendValues
        childUpdates[USER_FRIENDS + SEP + friendId + SEP + userId] = userValues

        databaseReference.root.updateChildren(childUpdates)
    }

    fun removeFriendRequest(userId: String, friendId: String) {
        val childUpdates = HashMap<String, Any?>()
        childUpdates[USER_FRIENDS + SEP + userId + SEP + friendId] = null

        databaseReference.root.updateChildren(childUpdates)
    }

    fun checkType(userId: String, friendId: String): Query {
        return databaseReference.root.child(USER_FRIENDS).child(userId).child(friendId)
    }

    companion object {

        private val TAG = "UserRepository"

        val currentId: String
            get() = Objects.requireNonNull<FirebaseUser>(FirebaseAuth.getInstance().currentUser).getUid()
    }
}
