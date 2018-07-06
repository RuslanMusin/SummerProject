package com.summer.itis.summerproject.model.repository.json;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.summer.itis.summerproject.model.Card;
import com.summer.itis.summerproject.model.Question;
import com.summer.itis.summerproject.model.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

import static com.summer.itis.summerproject.utils.Const.SEP;

public class TestRepository {

    private DatabaseReference databaseReference;

    private final String TABLE_NAME = "tests";
    private final String USER_TESTS = "user_tests";
    private final String TEST_QUESTIONS = "test_questions";
    private final String TEST_CARDS = "test_cards";

    private final String FIELD_ID = "id";
    private final String FIELD_TITLE = "title";
    private final String FIELD_TEST_PHOTO = "photoUrl";
    private final String FIELD_OWNER_ID = "ownerId";
    private final String FIELD_DESC=  "desc";


    public TestRepository() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(TABLE_NAME);
    }

    public Map<String, Object> toMap(Test test) {
        HashMap<String, Object> result = new HashMap<>();

        result.put(FIELD_ID,test.getId());
        result.put(FIELD_DESC, test.getDesc());
        result.put(FIELD_TITLE, test.getTitle());
        result.put(FIELD_OWNER_ID, test.getOwnerId());
        result.put(FIELD_TEST_PHOTO, test.getPhotoUrl());

        return result;
    }

    public Map<String, Object> toMap(String id) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(FIELD_ID, id);
        return result;
    }

    public Map<String, Object> toMap(Question question) {
        HashMap<String, Object> result = new HashMap<>();
        String id = databaseReference.getRoot().child(TEST_QUESTIONS).push().getKey();
        result.put(FIELD_ID, id);
        return result;
    }

    public void createTest(Test test, String userId) {
        String crossingKey = databaseReference.push().getKey();
        test.setId(crossingKey);

        Map<String, Object> crossingValues = toMap(test);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(TABLE_NAME + "/" + crossingKey, crossingValues);

        QuestionRepository questionRepository = new QuestionRepository();
        questionRepository.setDatabaseReference(TEST_QUESTIONS + SEP + test.getId());
        for(Question question : test.getQuestions()) {

            Map<String, Object> crossingIdValues = questionRepository.toMap(question);
            childUpdates.put(TEST_QUESTIONS + SEP + test.getId() + SEP + question.getId(),crossingIdValues);
        }

        CardRepository cardRepository = new CardRepository();
        cardRepository.setDatabaseReference(TEST_CARDS + SEP + test.getId());
        for(Card card : test.getCards()) {

            Map<String, Object> crossingIdValues = cardRepository.toMap(card);
            childUpdates.put(TEST_CARDS + SEP + test.getId() + SEP + card.getId(),crossingIdValues);
        }

        databaseReference.getRoot().updateChildren(childUpdates);
    }

    public DatabaseReference readCrossing(String pointId) {
        return databaseReference.child(pointId);
    }

    public void deleteCrossing(String pointId){
        databaseReference.child(pointId).removeValue();
    }

    public Single<Query> loadDefaultCrossings() {
        return Single.just(databaseReference.limitToFirst(100));
    }

    public Single<Query> loadByUser(String userId) {
        return Single.just(databaseReference.getRoot().child(USER_TESTS).child(userId));
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

    public Single<List<Query>> loadByIds(List<String> crossingsIds) {
        List<Query> queries = new ArrayList<>();
        for(String id : crossingsIds) {
            queries.add(databaseReference.child(id));
        }
        return Single.just(queries);

    }
}
