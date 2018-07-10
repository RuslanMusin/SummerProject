package com.summer.itis.summerproject.repository.json;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.summer.itis.summerproject.api.Card;

import java.util.HashMap;
import java.util.Map;

public class CardRepository {

    private DatabaseReference databaseReference;

    public final String TABLE_NAME = "test_cards";

    private final String FIELD_ID = "id";
    private final String FIELD_WIKI = "wikiUrl";
    private final String FIELD_INTELLIGENCE = "intelligence";
    private final String FIELD_SUPPORT = "support";
    private final String FIELD_PRESTIGE = "prestige";
    private final String FIELD_HP= "hp";
    private final String FIELD_STRENGTH = "strength";


    public CardRepository() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(TABLE_NAME);
    }

    public Map<String, Object> toMap(Card card) {
        HashMap<String, Object> result = new HashMap<>();

        String id = databaseReference.push().getKey();
        card.setId(id);
        result.put(FIELD_ID,card.getId());
        result.put(FIELD_WIKI, card.getWikiUrl());
        result.put(FIELD_INTELLIGENCE, card.getIntelligence());
        result.put(FIELD_PRESTIGE, card.getPrestige());
        result.put(FIELD_HP, card.getHp());
        result.put(FIELD_SUPPORT, card.getSupport());
        result.put(FIELD_STRENGTH, card.getStrength());

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
