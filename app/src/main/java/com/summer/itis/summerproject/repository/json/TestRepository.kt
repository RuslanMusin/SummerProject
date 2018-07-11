package com.summer.itis.summerproject.repository.json

import android.util.Log
import com.google.firebase.database.*
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.model.User

import java.util.ArrayList
import java.util.HashMap

import io.reactivex.Single

import com.summer.itis.summerproject.utils.Const.SEP
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.RxUtils

class TestRepository : Listener {


    private val databaseReference: DatabaseReference

    private val TABLE_NAME = "tests"
    private val USER_TESTS = "user_tests"
    private val TEST_QUESTIONS = "test_questions"
    private val TEST_CARDS = "test_cards"
    private val ABSTRACT_CARDS = "abstract_cards"


    private val FIELD_ID = "id"
    private val FIELD_TITLE = "title"
    private val FIELD_CARD_ID = "cardId"
    private val FIELD_AUTHOR_ID = "authorId"
    private val FIELD_AUTHOR_NAME = "authorName"
    private val FIELD_QUESTIONS = "questions"
    private val FIELD_DESC = "desc"

    init {
        this.databaseReference = FirebaseDatabase.getInstance().reference.child(TABLE_NAME)
    }

    fun toMap(test: Test): Map<String, Any?> {
        val result = HashMap<String, Any?>()

        result[FIELD_ID] = test.id
        result[FIELD_DESC] = test.desc
        result[FIELD_TITLE] = test.title
        result[FIELD_AUTHOR_ID] = test.authorId
        result[FIELD_AUTHOR_NAME] = test.authorName
        result[FIELD_CARD_ID] = test.cardId
        result[FIELD_QUESTIONS] = test.questions

        return result
    }

    fun toMap(id: String): Map<String, Any> {
        val result = HashMap<String, Any>()
        result[FIELD_ID] = id
        return result
    }

    fun toMap(question: Question): Map<String, Any?> {
        val result = HashMap<String, Any?>()
        val id = databaseReference.root.child(TEST_QUESTIONS).push().key
        result[FIELD_ID] = id
        return result
    }

    override fun createTest(test: Test, user: User,type: String) {
        val card = test.card
        val abstractCardRepository = AbstracCardRepository()
        if(type.equals("read")) {
            Log.d(TAG_LOG,"read")
            abstractCardRepository.findAbstractCard(test, user,this)
        } else {
            val childUpdates = HashMap<String, Any>()
            Log.d(TAG_LOG,"create")
            val crossingKey = databaseReference.push().key
            test.id = crossingKey

            Log.d(TAG_LOG, "abstract")
            val abstractCard = card?.abstractCard
            val cardId = abstractCard?.id
            if (cardId == null) {
                Log.d(TAG_LOG,"createAbs")
                val abstractCardValues = abstractCardRepository.toMap(abstractCard)
                childUpdates[ABSTRACT_CARDS + SEP + abstractCard?.id] = abstractCardValues
                card?.cardId = abstractCard?.id
                card?.testId = test.id
            } else {
                Log.d(TAG_LOG,"no create abs")
            }
            Log.d(TAG_LOG, "after abstract")
            val cardRepository = CardRepository()
            val crossingIdValues = cardRepository.toMap(card)
            childUpdates[TEST_CARDS + SEP + card?.id] = crossingIdValues


            test.authorId = user.id
            test.authorName = user.username
            test.cardId = card?.id

            val crossingValues = toMap(test)

            childUpdates["$TABLE_NAME/$crossingKey"] = crossingValues

            val questionRepository = QuestionRepository()
            questionRepository.setDatabaseReference(TEST_QUESTIONS + SEP + test.id)
           /* for (question in test.questions) {

                val crossingIdValues = questionRepository.toMap(question)
                childUpdates[TEST_QUESTIONS + SEP + test.id + SEP + question.id] = crossingIdValues

                *//*for(answer in question.answers) {
                    val answerValues = answerRepository.toMap(answer)
                    childUpdates[TEST_ANSWERS + SEP + ]
                }*//*
            }*/


            databaseReference.root.updateChildren(childUpdates)
        }
    }

    fun readTest(testId: String?): Single<Test> {
        var test: Test?
        val query: Query = databaseReference.child(testId!!)
        val single : Single<Test> =  Single.create { e ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    test = dataSnapshot.getValue(Test::class.java)
                    test?.let { e.onSuccess(it) }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        }
        return single.compose(RxUtils.asyncSingle())
    }


    fun deleteCrossing(pointId: String) {
        databaseReference.child(pointId).removeValue()
    }

    fun loadDefaultTests(): Single<MutableList<Test?>> {
        var flag: Boolean = false
        var tests: MutableList<Test?>? = ArrayList()
        val query: Query = databaseReference.limitToFirst(100)
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(shapshot: DataSnapshot in dataSnapshot.children) {
                    val test = shapshot.getValue(Test::class.java)
                    tests?.add(test)
                }
                flag = true
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        while(!flag) {

        }
        return Single.just(tests)
    }

    fun loadByUser(userId: String): Single<Query> {
        return Single.just(databaseReference.root.child(USER_TESTS).child(userId))
    }

    /* public Single<Map<String,Query>> loadByBook(Book book) {
        Map<String, Query> queryMap = new HashMap<>();
        DatabaseReference reference = databaseReference.getRoot().child(BOOK_CROSSINGS);
        Query queryId =  reference.child(book.getId());

        reference = databaseReference.getRoot().child(CROSSINGS_QUERY);
        Query queryName;
        List<String> authors = book.getAuthors();
        String bookName = book.getName().trim();
        if(authors.size() > 1) {
            queryName =  reference.orderByChild(FIELD_BOOK_NAME).startAt(bookName).endAt(bookName + QUERY_END);
        } else {
            String[] parts = authors.get(0).trim().split("\\s+");
            String lastPart = parts[parts.length-1];
            queryName = reference.orderByChild(FIELD_BOOK_NAME).startAt(bookName).endAt(bookName + QUERY_END);
//            queryName = queryName.orderByChild(FIELD_BOOK_AUTHOR).endAt(lastPart);
        }
        Log.d(TAG_LOG, "query is null ? " + (queryName == null));
        Log.d(TAG_LOG, "queryId is null ? " + (queryId == null));

        queryMap.put("queryId",queryId);
        queryMap.put("queryName",queryName);
        return Single.just(queryMap);
    }

    public Single<Query> loadByQuery(String query) {
        Log.d(TAG_LOG, "load cross by query = " + query);
        String queryPart = query.trim();
        DatabaseReference reference = databaseReference.getRoot().child(CROSSINGS_QUERY);
        Query queryName = reference.orderByChild(FIELD_BOOK_NAME).startAt(queryPart).endAt(queryPart + QUERY_END);
        Log.d(TAG_LOG, "query is null ? " + (queryName == null));
        return Single.just(queryName);
    }*/

    fun loadByIds(crossingsIds: List<String>): Single<List<Query>> {
        val queries = ArrayList<Query>()
        for (id in crossingsIds) {
            queries.add(databaseReference.child(id))
        }
        return Single.just(queries)

    }
}
