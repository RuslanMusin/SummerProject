package com.summer.itis.summerproject.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.Expose

@IgnoreExtraProperties
class AbstractCard {

    var id: String? = null

    var name: String? = null

    var photoUrl: String? = null

    var extract: String? = null

    var description: String? = null

    var wikiUrl: String? = null

    @field:Exclude var isOwner: Boolean = false
}
