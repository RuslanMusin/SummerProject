package com.summer.itis.summerproject.repository.json;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.summer.itis.summerproject.model.Question;

import java.util.HashMap;
import java.util.Map;

public class QuestionRepository {

    private DatabaseReference databaseReference;

    public final String TABLE_NAME = "questions";

    private final String FIELD_ID = "id";
    private final String FIELD_QUESTION = "question";
    private final String FIELD_PHOTO = "photoUrl";
    private final String FIELD_WIKI = "wikiUrl";
    private final String FIELD_ANSWERS = "answers";
    private final String FIELD_RIGHT_ANSWERS = "right_answers";

    public QuestionRepository() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(TABLE_NAME);
    }

    public Map<String, Object> toMap(Question question) {
        HashMap<String, Object> result = new HashMap<>();

        String id = databaseReference.push().getKey();
        question.setId(id);
        result.put(FIELD_ID,question.getId());
        result.put(FIELD_QUESTION, question.getQuestion());
        result.put(FIELD_ANSWERS, question.getAnswers());

        return result;
    }


    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(String path) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(TABLE_NAME);
    }

    public String getKey(String crossingId){
        return databaseReference.child(crossingId).push().getKey();
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
