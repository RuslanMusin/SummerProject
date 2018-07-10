package com.summer.itis.summerproject.repository.json

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GamesRepository {
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val TABLE_NAME = "games"


}