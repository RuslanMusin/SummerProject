package com.summer.itis.summerproject.model

import com.google.firebase.database.IgnoreExtraProperties
import com.summer.itis.summerproject.api.Card

@IgnoreExtraProperties
class User {

    var id: String? = null

    var email: String? = null

    var username: String? = null

    var photoUrl: String? = null

    var desc: String? = null

    var score: String? = null

    private val cards: List<Card>? = null

    private val tests: List<Test>? = null

    private val role: String? = null

    constructor() {}

    constructor(email: String, username: String) {
        this.email = email
        this.username = username
    }
}
