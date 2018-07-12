package com.summer.itis.summerproject.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.summer.itis.summerproject.model.AbstractCard

@IgnoreExtraProperties
class Card {

    var id: String? = null

    var cardId: String? = null

    var testId: String? = null

    var intelligence: Int? = null

    var support: Int? = null

    var prestige: Int? = null

    var hp: Int? = null

    var strength: Int? = null

    @Exclude
    var abstractCard: AbstractCard? = AbstractCard()

    @Exclude
    var test: Test = Test()
}
