package com.summer.itis.summerproject.model.db_dop_models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.Expose

import java.util.HashMap

//ОТНОШЕНИЕ МЕЖДУ ЮЗЕРАМИ(ДРУЗЬЯ И Т.Д)
@IgnoreExtraProperties
class Relation : ElementId() {

    var relation: String? = null

    @Expose
    var relBefore: String? = null

    companion object {

        private val FIELD_RELATION = "relation"

        protected val FIELD_ID = "id"

        fun toMap(id: String, relation: String): Map<String, Any> {
            val result = HashMap<String, Any>()
            result[FIELD_ID] = id
            result[FIELD_RELATION] = relation
            return result
        }
    }
}
